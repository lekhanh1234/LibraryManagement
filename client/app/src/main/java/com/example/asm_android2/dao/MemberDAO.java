package com.example.asm_android2.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.asm_android2.modal.Librarian;
import com.example.asm_android2.modal.Member;

public class MemberDAO {
    private LibraryDB dbThuVien;

    public MemberDAO(LibraryDB dbThuVien) {
        this.dbThuVien = dbThuVien;
    }

    public List<Member> getAllMember(){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from memberThuthu where idthuthu="+ Librarian.getId(),null);
        List<Member> list=new ArrayList<>();
        while (cursor.moveToNext()){
            int idMember=cursor.getInt(0);
            String madinhdanh=cursor.getString(1);
            String name=cursor.getString(2);
            list.add(new Member(idMember,madinhdanh,name, Librarian.getNameThuthu()));
        }
        cursor.close();
        if(list.size()==0) return null;
        return list;
    }

    public boolean insertMember(Member member){
        SQLiteDatabase db=dbThuVien.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",member.getIdMember());
        values.put("madinhdanh",member.getMadinhdanh());
        values.put("name",member.getNameMember());
        values.put("idthuthu", Librarian.getId());
        db.insert("memberThuthu", null, values);
        return true;
    }
    public String getNameById(int id){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from memberThuthu where id="+id,null);
        String name=null;
        while (cursor.moveToNext())
            name=cursor.getString(2);
        cursor.close();
        return name;
    }
    public int getIdByDinhdanh(String dinhdanh){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from memberThuthu where madinhdanh='"+dinhdanh+"'",null);
        int id=-1;
        while (cursor.moveToNext())
            id=cursor.getInt(0);
        cursor.close();
        return id;
    }
    public String getDinhdanhById(int id){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from memberThuthu where id="+id,null);
        String dinhdanh=null;
        while (cursor.moveToNext())
            dinhdanh=cursor.getString(1);
        cursor.close();
        return dinhdanh;
    }
    public void deleteMemberbyId(int id){
        SQLiteDatabase database=dbThuVien.getWritableDatabase();
        database.delete("memberThuthu","id= ?",new String[]{id+""});
    }

}
