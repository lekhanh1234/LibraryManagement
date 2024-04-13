package com.example.asm_android2.dataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asm_android2.Account.AccountThuThuLogin;
import com.example.asm_android2.Account.AccountThuthu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DAOThuthu {
    private DATABASEThuvien dbThuVien;

    public DAOThuthu(DATABASEThuvien dbThuVien) {
        this.dbThuVien = dbThuVien;
    }
    //    db.execSQL("create table productOfAccount(id INTEGER PRIMARY KEY autoincrement
    //   ,nameUser nvarchar(50) not null,passWordUser nvarchar(50), nameProduct nvarchar(50) not null ,price int not null,amount int not null)");
    public String getNameById(int id){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();

        Cursor cursor=db.rawQuery("select * from accountThuthu where id="+id,null);
        List<AccountThuthu> list=new ArrayList<>();
        while (cursor.moveToNext())
        {
            String nameThuThu=cursor.getString(4);
            cursor.close();
            return nameThuThu;
        }
        return null;
    }
    public int getIdByDinhdanh(String Dinhdanh){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();

        Cursor cursor=db.rawQuery("select * from accountThuthu where dinhdanh='"+Dinhdanh+"'",null);
        List<AccountThuthu> list=new ArrayList<>();
        while (cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            cursor.close();
            return id;
        }
        cursor.close();
        return -1;
    }

    public boolean checkPassword(String password){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from accountThuthu",null);
        cursor.moveToNext();
        String passWordFromTable= cursor.getString(2);
        {
            Log.d("value 2 pw:"+password+":"+passWordFromTable, "value 2 pw:"+password+":"+passWordFromTable);
        }
        if(password.equals(passWordFromTable)) return true;
        return false;
    }
    public void changePassWord(String newPassWord,String passWord){

        SQLiteDatabase db=dbThuVien.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("passWord",newPassWord);
        db.update("accountThuthu",values, "passWord = ?", new String[]{passWord});
        new Thread(new Runnable() {
            @Override
            public void run() {
                changePassWordFromSever(newPassWord,AccountThuThuLogin.getId());
            }
        }).start();
        return;
    }
    public void changePassWordFromSever(String newPassWord,int idthuthu){
        try{
            String Post_Url = "http://192.168.43.189:8080/sever_messenger/changePassWordThuThu";
            URL urlSever_Post = new URL(Post_Url);
            String PARAMS = "idthuthu="+idthuthu+"&newpassword="+newPassWord;
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(PARAMS.getBytes());
            os.flush();
            httpURLConnection.getResponseCode();
        } catch (Exception e){
            Log.d("co loi xay ra", e.toString());
        }
    }


}
