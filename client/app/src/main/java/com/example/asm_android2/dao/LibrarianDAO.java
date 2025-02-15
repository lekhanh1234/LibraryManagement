package com.example.asm_android2.dao;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.asm_android2.ServerService.PrepareMethod;
import com.example.asm_android2.modal.Admin;
import com.example.asm_android2.modal.Librarian;
import com.example.asm_android2.modal.LibrarianAdminControl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LibrarianDAO {
    private LibraryDB dbLibrary;
    public LibrarianDAO(LibraryDB dbLibrary) {
        this.dbLibrary = dbLibrary;
    }
    public boolean addLibrarianAccountToSever(String name,String username,String password,String dinhdanh){
        int idThuThuFromSever[]=new int[1];
        Thread a=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String Post_Url = "http://192.168.43.189:8080/sever_messenger/insertAccountThuThu";
                    String PARAMS = "name=" +name+"&username=" +username+"&password="+password+"&dinhdanh="+dinhdanh;
                    HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(Post_Url,"Post");
                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(PARAMS.getBytes());
                    os.flush();
                    int codeResponse= httpURLConnection.getResponseCode();
                    if(codeResponse==403) {
                        idThuThuFromSever[0] = -1;
                        return;
                    }
                    BufferedReader bf=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    idThuThuFromSever[0]=Integer.parseInt(bf.readLine());
                }
                catch (Exception e){
                }
            }
        });
        a.start();
        try{
            a.join();
        } catch (Exception e){}
        if(idThuThuFromSever[0]==-1) return false;
        Admin.getLibrarianAdminControlList().add(new LibrarianAdminControl(idThuThuFromSever[0],username,password,name,dinhdanh));
        return true;
    }

    public String getNameById(int id){
        SQLiteDatabase db=dbLibrary.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from librarian where id="+id,null);
        if(cursor.moveToNext())
        {
            String name=cursor.getString(4);
            cursor.close(); db.close();
            return name;
        }
        return null;
    }
    public int getIdByDinhdanh(String Dinhdanh){
        SQLiteDatabase db=dbLibrary.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from librarian where dinhdanh='"+Dinhdanh+"'",null);
        int id = -1;
        if(cursor.moveToNext())
        {
            id = cursor.getInt(0);
            return id;
        }
        cursor.close(); db.close();
        return id;
    }

    public boolean checkPassword(String password){
        SQLiteDatabase db=dbLibrary.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from librarian where id ="+ Librarian.getId(),null);
        cursor.moveToNext();
        String passwordFromTable= cursor.getString(2);
        if(password.equals(passwordFromTable)) return true;
        return false;
    }
    public void changePassWord(String newPassword,String passWord){
        SQLiteDatabase db=dbLibrary.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password",newPassword);
        db.update("librarian",values, "password = ?", new String[]{passWord});
        new Thread(new Runnable() {
            @Override
            public void run() {
                changePassWordOnSever(newPassword, Librarian.getId());
            }
        }).start();
    }
    public void changePassWordOnSever(String newPassWord,int idLibrarian){
        try{
            String url = "http://192.168.43.189:8080/sever_messenger/changePassWordThuThu";
            String PARAMS = "idLibrarian="+idLibrarian+"&newPassword="+newPassWord;
            HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(url,"POST");
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(PARAMS.getBytes());
            os.flush();
            httpURLConnection.getResponseCode();
        } catch (Exception e){
        }
    }
}
