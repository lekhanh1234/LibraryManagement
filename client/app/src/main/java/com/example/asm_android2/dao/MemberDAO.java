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
import java.util.ArrayList;
import java.util.List;

import com.example.asm_android2.ServerService.PrepareMethod;
import com.example.asm_android2.modal.Librarian;
import com.example.asm_android2.modal.Member;

public class MemberDAO {
    private LibraryDB dbThuVien;

    public MemberDAO(LibraryDB dbThuVien) {
        this.dbThuVien = dbThuVien;
    }

    public List<Member> getAllMember(){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from Member where idLibrarian="+ Librarian.getId(),null);
        List<Member> list=new ArrayList<>();
        while (cursor.moveToNext()){
            int idMember=cursor.getInt(0);
            String dinhdanh=cursor.getString(1);
            String name=cursor.getString(2);
            list.add(new Member(idMember,dinhdanh,name, Librarian.getName()));
        }
        cursor.close();
        if(list.size()==0) return null;
        return list;
    }

    public boolean insertMember(String dinhdanh, String memberName){
        SQLiteDatabase db=dbThuVien.getWritableDatabase();
        ContentValues values = new ContentValues();
        int idMember[] = new int[1];
        if( (idMember[0] = getIdByDinhdanh(dinhdanh)) ==-1) { // -1 k co member ton tai
            Thread addMember = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String url = "http://192.168.43.189:8080/sever_messenger/insertMember";
                        String PARAMS = "dinhdanhmember=" + dinhdanh + "&tenmember=" + memberName + "&idLibrarian=" + Librarian.getId();
                        HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(url, "POST");
                        OutputStream os = httpURLConnection.getOutputStream();
                        os.write(PARAMS.getBytes());
                        os.flush();
                        httpURLConnection.getResponseCode();
                        InputStream is = httpURLConnection.getInputStream();
                        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                        idMember[0] = Integer.parseInt(bf.readLine());
                    } catch (Exception e) {
                        Log.d("co loi xay ra", e.toString());
                    }
                }
            });
            addMember.start();
            try {
                addMember.join();
            } catch (Exception e) {
            }
        }

        values.put("id",idMember[0]);
        values.put("dinhdanh",dinhdanh);
        values.put("name",memberName);
        values.put("idLibrarian", Librarian.getId());
        db.insert("Member", null, values);
        return true;
    }
    public String getNameById(int id){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from Member where id="+id,null);
        String name=null;
        while (cursor.moveToNext())
            name=cursor.getString(2);
        cursor.close();
        return name;
    }
    public int getIdByDinhdanh(String dinhdanh){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from Member where dinhdanh='"+dinhdanh+"'",null);
        int id=-1;
        while (cursor.moveToNext())
            id=cursor.getInt(0);
        cursor.close();
        return id;
    }
    public String getDinhdanhById(int id){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from Member where id="+id,null);
        String dinhdanh=null;
        if(cursor.moveToNext())
            dinhdanh=cursor.getString(1);
        cursor.close();
        return dinhdanh;
    }
    public void deleteMemberbyId(int id){
        SQLiteDatabase database=dbThuVien.getWritableDatabase();
        database.delete("memberThuthu","id= ?",new String[]{id+""});
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String URL = "http://192.168.43.189:8080/sever_messenger/deleteMember?idmember=" + id;
                    HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(URL, "DELETE");
                    httpURLConnection.getResponseCode();
                } catch (Exception e) {
                }
            }
        }).start();
    }
}
