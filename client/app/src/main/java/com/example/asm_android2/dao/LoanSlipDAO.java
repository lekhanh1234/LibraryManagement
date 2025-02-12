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

import com.example.asm_android2.ServerService.PrepareMethod;
import com.example.asm_android2.modal.Librarian;
import com.example.asm_android2.modal.Member;
import com.example.asm_android2.modal.LoanSlip;

public class LoanSlipDAO {
    private LibraryDB dbLibrary;

    public LoanSlipDAO(LibraryDB dbLibrary) {
        this.dbLibrary = dbLibrary;
    }

    public List<LoanSlip> getAllLoanSlip() {
        SQLiteDatabase db = dbLibrary.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from loanSlip where idLibrarian=" + Librarian.getId(), null);
        List<LoanSlip> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String receiptNumber = cursor.getString(1);
            int idBook = cursor.getInt(3);
            int idMember = cursor.getInt(4);
            int states = cursor.getInt(5);
            String rentalDate = cursor.getString(6);
            String deadline = cursor.getString(7);
            list.add(new LoanSlip(id, receiptNumber, Librarian.getId(), idBook, idMember, states, rentalDate, deadline));
        }
        cursor.close();
        db.close();
        if (list.size() == 0) return null;
        return list;
    }

    public void insertLoanSlip(LoanSlip loanSlip) {
        SQLiteDatabase db = dbLibrary.getWritableDatabase();
        int idNewLoanSlip[] = new int[1];
        Thread addNewLoanSlip = new Thread(new Runnable() {
            @Override
            public void run() {
                idNewLoanSlip[0] = addLoanSlipToSever(loanSlip.getReceiptNumber(),Librarian.getId(),loanSlip.getIdBook(),loanSlip.getIdMember(),loanSlip.getStates(),loanSlip.getRentalDate(),loanSlip.getDeadline());
            }
        });
        addNewLoanSlip.start();
        try {
            addNewLoanSlip.join();
        } catch (Exception e) {
        }
        ContentValues values = new ContentValues();
        values.put("id", idNewLoanSlip[0]);
        values.put("receiptNumber", loanSlip.getReceiptNumber());
        values.put("idLibrarian", Librarian.getId());
        values.put("idBook", loanSlip.getIdBook());
        values.put("id_member", loanSlip.getIdMember());
        values.put("states", loanSlip.getStates());
        values.put("rentalDate", loanSlip.getRentalDate());
        values.put("deadline", loanSlip.getDeadline());
        db.insert("loanSlip", null, values);
    }

    public boolean checkLoanSlipByIdBook(int idBook) {
        //tra ve true neu ton tai 1 hoac nhieu phieu muon voi id sach da cho/
        // tra ve false neu khong co phieu muon nao voi id sach da cho
        SQLiteDatabase db = dbLibrary.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from loanSlip where idBook=" + idBook, null);
        return cursor.moveToNext();
    }

    public void changeStates(String receiptNumber, int states) {
        SQLiteDatabase db = dbLibrary.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("states", states);
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateStatusFromSever(Librarian.getId(), receiptNumber, states);
            }
        }).start();
        db.update("loanSlip", values, "receiptNumber = ?", new String[]{receiptNumber});
    }

    public void deleteLoanSlip(String receiptNumber) {
        SQLiteDatabase db = dbLibrary.getWritableDatabase();
        // co member voi ma phieu nay
        Cursor cursorTableLoanSlip = db.rawQuery("select * from loanSlip where receiptNumber='" + receiptNumber + "'", null);
        if (cursorTableLoanSlip.moveToNext() == false)
            return; // không tồn tại phiếu với biên lai trên
        int idMember = cursorTableLoanSlip.getInt(4);
        db.delete("loanSlip", "receiptNumber = ?", new String[]{receiptNumber});
        //xoa phieu muon tren sever;
        new Thread(new Runnable() {
            @Override
            public void run() {
                deleteLoanSlipFromSever(receiptNumber, Librarian.getId());
            }
        }).start();
        cursorTableLoanSlip = db.rawQuery("select * from loanSlip where idMember =" + idMember, null);
        if (cursorTableLoanSlip.moveToNext()) return; // member trên vẫn còn trong nhiều phiếu khác
        else {
            // xoa member voi idmerber phieu vua xoa
            MemberDAO memberDAO = new MemberDAO(dbLibrary);
            memberDAO.deleteMemberbyId(idMember);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    memberDAO.deleteMemberbyId(idMember);
                }
            }).start();
        }
    }
    public int addLoanSlipToSever(String receiptNumber, int idLibrarian, int idbook, int idmember, int states, String rentalDate, String deadline) {
        try {
            String url = "http://192.168.43.189:8080/sever_messenger/insertPhieuMuon";
            String PARAMS = "receiptNumber=" + receiptNumber + "&idLibrarian=" + idLibrarian + "&idbook=" + idbook + "&idmember=" + idmember + "&states=" + states + "&ngaythue=" + rentalDate + "&deadline=" + deadline;
            HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(url, "POST");
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(PARAMS.getBytes());
            os.flush();
            httpURLConnection.getResponseCode();
            InputStream is = httpURLConnection.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            return Integer.parseInt(bf.readLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public void deleteLoanSlipFromSever(String receiptNumber, int idLibrarian) {
        try {
            String url = "http://192.168.43.189:8080/sever_messenger/deleteLoanSlip?receiptNumber=" + receiptNumber + "&idLibrarian=" + idLibrarian;
            HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(url, "DELETE");
            httpURLConnection.getResponseCode();
        } catch (Exception e) {
        }
    }

    public void updateStatusFromSever(int idLibrarian, String receiptNumber, int states) {
        try {
            String URL = "http://192.168.43.189:8080/sever_messenger/updateTrangThaiPhieu";
            String PARAMS = "idLibrarian=" + idLibrarian + "&receiptNumber=" + receiptNumber + "&states=" + states;
            HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(URL, "POST");
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(PARAMS.getBytes());
            os.flush();
            httpURLConnection.getResponseCode();
        } catch (Exception e) {
        }
    }
}
