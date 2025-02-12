package com.example.asm_android2.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.asm_android2.modal.Librarian;
import com.example.asm_android2.modal.Member;
import com.example.asm_android2.modal.LoanSlip;

public class LoanSlipDAO {
    private LibraryDB dbLibrary;
    public LoanSlipDAO(LibraryDB dbLibrary) {
        this.dbLibrary = dbLibrary;
    }
    public List<LoanSlip>  getAllLoanSlip(){
        SQLiteDatabase db=dbLibrary.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from loanSlip where idLibrarian="+Librarian.getId(),null);
        List<LoanSlip> list=new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String receiptNumber=cursor.getString(1);
            int idBook=cursor.getInt(3);
            int idMember=cursor.getInt(4);
            int states =cursor.getInt(5);
            String rentalDate=cursor.getString(6);
            String deadline =cursor.getString(7);
         list.add(new LoanSlip(id,receiptNumber,Librarian.getId(),idBook,idMember,states,rentalDate,deadline));
        }
        cursor.close();
        db.close();
        if(list.size()==0) return null;
        return list;
    }

    public void insertLoanSlip(LoanSlip loanSlip){
        SQLiteDatabase db=dbLibrary.getWritableDatabase();
        MemberDAO memberDAO =new MemberDAO(dbLibrary);
        BookDAO bookDao =new BookDAO(dbLibrary);
        int idBook= bookDao.getIdByMaSach(loanSlip.getMasach());
        int idMember[]=new int[1];
        if((idMember[0]= memberDAO.getIdByDinhdanh(loanSlip.getDinhdanhMember()))==-1){ // -1 k co member ton tai
            // gui thong tin member len sever tai vi member chua ton tai khi ket qua la -1, nguoc lai khong can su li gi them
            Thread addMember=new Thread(new Runnable() {
                @Override
                public void run() {
                    idMember[0]=addMemberToSever(loanSlip.getDinhdanhMember(), loanSlip.getMember(), Librarian.getId());
                    Log.d("idmember duoc tra ve :"+idMember, "run: ");
                    memberDAO.insertMember(new Member(idMember[0], loanSlip.getDinhdanhMember(), loanSlip.getMember(), Librarian.getNameThuthu()));
                }
            });
            addMember.start();
            try {
                addMember.join();
            } catch (Exception e){}
        }

        int idphieuAdd[]=new int[1];
            Thread addPhieu = new Thread(new Runnable() {
                @Override
                public void run() {
                    idphieuAdd[0]= addPhieuMuonToSever(loanSlip.getMaphieu(), Librarian.getId(), idBook, idMember[0], loanSlip.getTrangthai(), loanSlip.getNgaythue(), loanSlip.getThoihan());
                }
            });
            addPhieu.start();
            try {
                addPhieu.join();
            } catch (Exception e){}
        ContentValues values = new ContentValues();
        values.put("id",idphieuAdd[0]);
        values.put("receiptNumber", loanSlip.getMaphieu());
        values.put("idLibrarian", Librarian.getId());
        values.put("idBook", idBook);
        values.put("id_member", idMember[0]);
        values.put("tinhtrang", loanSlip.getTrangthai());
        values.put("ngaythue", loanSlip.getNgaythue());
        values.put("thoihan", loanSlip.getThoihan());
        db.insert("phieumuon", null, values);
    }
    public boolean checkPhieuMuonByIdBook(int idBook){
        //tra ve true neu ton tai 1 hoac nhieu phieu muon voi id sach da cho/
        // tra ve false neu khong co phieu muon nao voi id sach da cho
        SQLiteDatabase db=dbLibrary.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from phieumuon where idBook="+idBook,null);
        return cursor.moveToNext();
    }

    public void thayDoiTrangThai(String receiptNumber,int trangthai){
        SQLiteDatabase db=dbLibrary.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tinhtrang",trangthai);
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateStatusFromSever(Librarian.getId(),receiptNumber,trangthai);
            }
        }).start();
        db.update("phieumuon",values,"receiptNumber = ?", new String[]{receiptNumber});
        return;
    }

    public void deleteData(String receiptNumber) {
        SQLiteDatabase db = dbLibrary.getWritableDatabase();
        // co member voi ma phieu nay
        Cursor cursorTableLoanSlip=db.rawQuery("select * from phieumuon where receiptNumber='"+receiptNumber+"'",null);
        cursorTableLoanSlip.moveToNext();
        int idMember=cursorTableLoanSlip.getInt(4);
        db.delete("phieumuon", "receiptNumber = ?", new String[]{receiptNumber});
        //xoa phieu muon tren sever;
        new Thread(new Runnable() {
            @Override
            public void run() {
                deletePhieuFromSever(receiptNumber, Librarian.getId());
            }
        }).start();

        cursorTableLoanSlip=db.rawQuery("select * from phieumuon where id_member="+idMember,null);
        if(cursorTableLoanSlip.moveToNext()) return;
        else {
            // neu sau khi xoa phieu khong con phieu nao voi idmember trung voi idmember của phieu vua xoa
            // xoa member voi idmerber phieu vua xoa
            MemberDAO memberDAO =new MemberDAO(dbLibrary);
            memberDAO.deleteMemberbyId(idMember);
            // thuc hien xoa tiep tren sever;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteMemberFromSever(idMember);
                }
            }).start();

        }
        return;
    }
    public int addMemberToSever(String dinhdanhmember,String nameMember,int idThuthu){
        try{
            String Post_Url = "http://192.168.43.189:8080/sever_messenger/insertMember";
            URL urlSever_Post = new URL(Post_Url);
            String PARAMS = "dinhdanhmember="+dinhdanhmember+"&tenmember=" +nameMember+"&idLibrarian="+idThuthu;
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(PARAMS.getBytes());
            os.flush();
            httpURLConnection.getResponseCode();

            Log.d("thong diep tu sever", "");

            InputStream is=httpURLConnection.getInputStream();
            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
            return Integer.parseInt(bf.readLine());
        } catch (Exception e){
            Log.d("co loi xay ra", e.toString());
            return -10;
        }
        }
        public int addPhieuMuonToSever(String receiptNumber,int idLibrarian,int idbook,int idmember,int tinhtrang,String ngaythue,String thoihan){
            try{
                String Post_Url = "http://192.168.43.189:8080/sever_messenger/insertPhieuMuon";
                URL urlSever_Post = new URL(Post_Url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();

                String PARAMS = "receiptNumber="+receiptNumber+"&idLibrarian=" +idLibrarian+"&idbook="+idbook+"&idmember="+idmember+"&tinhtrang="+tinhtrang+"&ngaythue="+ngaythue+"&thoihan="+thoihan;
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(PARAMS.getBytes());
                os.flush();
                ///////////////////
                httpURLConnection.getResponseCode();
                InputStream is=httpURLConnection.getInputStream();
                BufferedReader bf=new BufferedReader(new InputStreamReader(is));
                return Integer.parseInt(bf.readLine());
            } catch (Exception e){
                Log.d("co loi xay ra", e.toString());
                return -10;
            }
        }
        public void deletePhieuFromSever(String receiptNumber,int idLibrarian){
            try{
                String Post_Url = "http://192.168.43.189:8080/sever_messenger/deleteLoanSlip?receiptNumber="+receiptNumber+"&idLibrarian="+idLibrarian;
                URL urlSever_Post = new URL(Post_Url);
                Log.d("idLibrarian len sever:"+idLibrarian, "deletePhieuFromSever: ");
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
                httpURLConnection.setRequestMethod("DELETE");
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.getResponseCode();
            } catch (Exception e){
            }
        }
        public void deleteMemberFromSever(int idmember){
            try{
                String Post_Url = "http://192.168.43.189:8080/sever_messenger/deleteMember?idmember="+idmember;
                URL urlSever_Post = new URL(Post_Url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
                httpURLConnection.setRequestMethod("DELETE");
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.getResponseCode();
            } catch (Exception e){
            }
        }
        public void updateStatusFromSever(int idLibrarian,String receiptNumber,int trangthai){
            try{
                String Post_Url = "http://192.168.43.189:8080/sever_messenger/updateTrangThaiPhieu";
                URL urlSever_Post = new URL(Post_Url);
                String PARAMS = "idLibrarian="+idLibrarian+"&receiptNumber=" +receiptNumber+"&trangthai="+trangthai;
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(PARAMS.getBytes());
                os.flush();
                httpURLConnection.getResponseCode();
            } catch (Exception e){
            }
        }

    }