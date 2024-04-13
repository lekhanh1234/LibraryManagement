package com.example.asm_android2.dataBase;

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
import java.util.Currency;
import java.util.List;

import com.example.asm_android2.Account.AccountThuThuLogin;
import com.example.asm_android2.infoManageThuThu.Member;
import com.example.asm_android2.infoManageThuThu.Phieumuon;

public class DAOPhieuMuon{
    private DATABASEThuvien dbThuVien;

    public DAOPhieuMuon(DATABASEThuvien dbThuVien) {

        this.dbThuVien = dbThuVien;
    }

    public List<Phieumuon>  getAllPhieuMuon(){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        DAOThuthu daoThuthu=new DAOThuthu(dbThuVien);
        int id=AccountThuThuLogin.getId();
        Cursor cursor=db.rawQuery("Select * from phieumuon where idthuthu="+id,null);
        List<Phieumuon> list=new ArrayList<>();

        while (cursor.moveToNext()){
            // lay ten ma phieu
            String maphieu=cursor.getString(1);
            // lay ten sach
            int idBook=cursor.getInt(3);
            DAOSach daoSach=new DAOSach(dbThuVien);
            String nameBook=daoSach.getNameById(idBook);
            // lay ten ma sach
            String maSach=daoSach.getMaSachById(idBook);
            // lay ten member
            int idMember=cursor.getInt(4);
            DAOMember daoMember=new DAOMember(dbThuVien);
            String nameMember=daoMember.getNameById(idMember);

            // lay dinh danh member
            String dinhdanhMember=daoMember.getDinhdanhById(idMember);


            int tinhtrang=cursor.getInt(5);
            String ngaythue=cursor.getString(6);
            String thoihan=cursor.getString(7);
            Log.d("ngaythue:",ngaythue );

         list.add(new Phieumuon(maphieu,id,nameBook,maSach,nameMember,dinhdanhMember,tinhtrang,ngaythue,thoihan));
        }
        cursor.close();
        Log.d("ket thuc ham", "getAllPhieuMuon: ");
        if(list.size()==0) return null;
        return list;
    }

    public void insertPhieumuon(Phieumuon phieumuon){
        SQLiteDatabase db=dbThuVien.getWritableDatabase();
        DAOMember daoMember=new DAOMember(dbThuVien);
        DAOSach daoSach=new DAOSach(dbThuVien);
        int idBook=daoSach.getIdByMaSach(phieumuon.getMasach());
        int idMember[]=new int[1];
        if((idMember[0]=daoMember.getIdByDinhdanh(phieumuon.getDinhdanhMember()))==-1){ // -1 k co member ton tai
            // gui thong tin member len sever tai vi member chua ton tai khi ket qua la -1, nguoc lai khong can su li gi them
            Thread addMember=new Thread(new Runnable() {
                @Override
                public void run() {
                    idMember[0]=addMemberToSever(phieumuon.getDinhdanhMember(),phieumuon.getMember(),AccountThuThuLogin.getId());
                    Log.d("idmember duoc tra ve :"+idMember, "run: ");
                    daoMember.insertMember(new Member(idMember[0],phieumuon.getDinhdanhMember(),phieumuon.getMember(),AccountThuThuLogin.getNameThuthu()));
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
                    idphieuAdd[0]= addPhieuMuonToSever(phieumuon.getMaphieu(), AccountThuThuLogin.getId(), idBook, idMember[0], phieumuon.getTrangthai(), phieumuon.getNgaythue(), phieumuon.getThoihan());
                }
            });
            addPhieu.start();
            try {
                addPhieu.join();
            } catch (Exception e){}
        ContentValues values = new ContentValues();
        values.put("id",idphieuAdd[0]);
        values.put("maphieu", phieumuon.getMaphieu());
        values.put("idthuthu", AccountThuThuLogin.getId());
        values.put("idBook", idBook);
        values.put("id_member", idMember[0]);
        values.put("tinhtrang", phieumuon.getTrangthai());
        values.put("ngaythue", phieumuon.getNgaythue());
        values.put("thoihan", phieumuon.getThoihan());
        db.insert("phieumuon", null, values);
    }
    public boolean checkPhieuMuonByIdBook(int idBook){
        //tra ve true neu ton tai 1 hoac nhieu phieu muon voi id sach da cho/
        // tra ve false neu khong co phieu muon nao voi id sach da cho
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from phieumuon where idBook="+idBook,null);
        return cursor.moveToNext();
    }

    public void thayDoiTrangThai(String maphieu,int trangthai){
        SQLiteDatabase db=dbThuVien.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tinhtrang",trangthai);
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateStatusFromSever(AccountThuThuLogin.getId(),maphieu,trangthai);
            }
        }).start();
        db.update("phieumuon",values,"maphieu = ?", new String[]{maphieu});
        return;
    }

    public void deleteData(String maphieu) {
        SQLiteDatabase db = dbThuVien.getWritableDatabase();
        // co member voi ma phieu nay
        Cursor cursorTablePhieumuon=db.rawQuery("select * from phieumuon where maphieu='"+maphieu+"'",null);
        cursorTablePhieumuon.moveToNext();
        int idMember=cursorTablePhieumuon.getInt(4);
        db.delete("phieumuon", "maphieu = ?", new String[]{maphieu});
        //xoa phieu muon tren sever;
        new Thread(new Runnable() {
            @Override
            public void run() {
                deletePhieuFromSever(maphieu,AccountThuThuLogin.getId());
            }
        }).start();

        cursorTablePhieumuon=db.rawQuery("select * from phieumuon where id_member="+idMember,null);
        if(cursorTablePhieumuon.moveToNext()) return;
        else {
            // neu sau khi xoa phieu khong con phieu nao voi idmember trung voi idmember của phieu vua xoa
            // xoa member voi idmerber phieu vua xoa
            DAOMember daoMember=new DAOMember(dbThuVien);
            daoMember.deleteMemberbyId(idMember);
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
            String PARAMS = "dinhdanhmember="+dinhdanhmember+"&tenmember=" +nameMember+"&idthuthu="+idThuthu;
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
        public int addPhieuMuonToSever(String maphieu,int idthuthu,int idbook,int idmember,int tinhtrang,String ngaythue,String thoihan){
            try{
                String Post_Url = "http://192.168.43.189:8080/sever_messenger/insertPhieuMuon";
                URL urlSever_Post = new URL(Post_Url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();

                String PARAMS = "maphieu="+maphieu+"&idthuthu=" +idthuthu+"&idbook="+idbook+"&idmember="+idmember+"&tinhtrang="+tinhtrang+"&ngaythue="+ngaythue+"&thoihan="+thoihan;
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
        public void deletePhieuFromSever(String maphieu,int idthuthu){
            try{
                String Post_Url = "http://192.168.43.189:8080/sever_messenger/deletePhieumuon?maphieu="+maphieu+"&idthuthu="+idthuthu;
                URL urlSever_Post = new URL(Post_Url);
                Log.d("idthuthu len sever:"+idthuthu, "deletePhieuFromSever: ");
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
        public void updateStatusFromSever(int idthuthu,String maphieu,int trangthai){
            try{
                String Post_Url = "http://192.168.43.189:8080/sever_messenger/updateTrangThaiPhieu";
                URL urlSever_Post = new URL(Post_Url);
                String PARAMS = "idthuthu="+idthuthu+"&maphieu=" +maphieu+"&trangthai="+trangthai;
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