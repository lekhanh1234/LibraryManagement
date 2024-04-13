package com.example.asm_android2.dataBase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.example.asm_android2.Account.AccountThuThuLogin;
import com.example.asm_android2.from_main;
import com.example.asm_android2.infoManageThuThu.Loaisach;
import com.example.asm_android2.login_thuthu;

public class DAOLoaiSach{
    private DATABASEThuvien dbThuVien;

    public DAOLoaiSach(DATABASEThuvien dbThuVien) {
        this.dbThuVien = dbThuVien;
    }

    public List<Loaisach> getAllLoaiSach(){
        SQLiteDatabase db=this.dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from loaisach where idthuthu="+AccountThuThuLogin.getId(),null);
        List<Loaisach> list=new ArrayList<>();
        while (cursor.moveToNext()){
            String masach=cursor.getString(1);
            String name=cursor.getString(2);
            list.add(new Loaisach(masach,name,AccountThuThuLogin.getId()));
        }
        cursor.close();
        return list;
    }
    public boolean insertLoaisach(Loaisach loaisach){

        List<Loaisach> list=getAllLoaiSach();
        int idthuthu=new DAOThuthu(dbThuVien).getIdByDinhdanh(AccountThuThuLogin.getIDdinhdanh());
        for(Loaisach x:list){
            if(x.getLoaisach().equalsIgnoreCase(loaisach.getLoaisach())) return false;
            if(x.getMasach().equalsIgnoreCase(loaisach.getMasach())) return false;
        }
        SQLiteDatabase db=dbThuVien.getWritableDatabase();

        int idtrave[]=new int[1];
        Thread InsertLoaiSachSever=new Thread(new Runnable() {
            @Override
            public void run() {
              idtrave[0]=InsertLoaiSachToSever(loaisach.getMasach(),loaisach.getLoaisach(),AccountThuThuLogin.getId());
            }
        });
        InsertLoaiSachSever.start();
        try{
            InsertLoaiSachSever.join();
        } catch (Exception e){}

        Log.d("id tra ve sever:"+idtrave[0],"");
        ContentValues content=new ContentValues();
        content.put("id",idtrave[0]);
        content.put("maloaisach",loaisach.getMasach());
        content.put("tenloaisach",loaisach.getLoaisach());
        content.put("idthuthu",AccountThuThuLogin.getId());
        db.insert("loaisach",null,content);
        return true;
    }
    public int amountCatolory(int id){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from book where idLoaisach ="+id+" and idthuthu="+AccountThuThuLogin.getId(),null);
        // dam bao ten loai sach chi co mot
        int amount=0;
        while (cursor.moveToNext()) amount++;
        cursor.close();
        return amount;
    }
    public int getIdByName(String name){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from loaisach where tenloaisach ='"+name+"' and idthuthu= " +AccountThuThuLogin.getId(),null);
        //dam bao ten loai sach chi co mot
        int id=-1;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            Log.d("idla:"+id, "getIdByName: ");
        }
        cursor.close();
        return id;
    }
    public void deleteLoaiSachById(int id){
        SQLiteDatabase db=dbThuVien.getWritableDatabase();
        db.delete("loaisach","id = ? ",new String[]{id+""});
        // xoa tiep sach tren sever
        new Thread(new Runnable() {
            @Override
            public void run() {
                deleteLoaiBookFromSever(id);
            }
        }).start();
    }
    public String getNamebyId(int idloaisach){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from loaisach where id ="+idloaisach+" and idthuthu="+AccountThuThuLogin.getId(),null);
        // dam bao ten loai sach chi co mot
        String name=null;
        if(cursor.moveToNext()) {
            Log.d("idloaisach:"+idloaisach, "getNamebyId: ");
            name = cursor.getString(2);
        }
        cursor.close();
        return name;
    }
    public int InsertLoaiSachToSever(String maloaisach,String tenLoaiSach,int idthuthu){
        try{
            String Post_Url = "http://192.168.43.189:8080/sever_messenger/insertCatoloryBook";
            URL urlSever_Post = new URL(Post_Url);
            String PARAMS = "maloaisach="+maloaisach+"&tenloaisach=" +tenLoaiSach+"&idthuthu="+idthuthu;
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(PARAMS.getBytes());
            os.flush();
            int Responsecode = httpURLConnection.getResponseCode();
            InputStream is=httpURLConnection.getInputStream();
            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
            return Integer.parseInt(bf.readLine());
        } catch (Exception e){
            Log.d("co loi xay ra", e.toString());
            return -10;
        }
    }
    public void deleteLoaiBookFromSever(int id){
        try{
            String Post_Url = "http://192.168.43.189:8080/sever_messenger/deleteLoaiBook?idloaisach="+id;
            URL urlSever_Post = new URL(Post_Url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.getResponseCode();
        } catch (Exception e){
        }
    }
}
