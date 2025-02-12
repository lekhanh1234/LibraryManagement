package com.example.asm_android2.dao;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.asm_android2.ServerService.PrepareMethod;
import com.example.asm_android2.modal.Librarian;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LibrarianDAO {
    private LibraryDB dbLibrary;
    public LibrarianDAO(LibraryDB dbLibrary) {
        this.dbLibrary = dbLibrary;
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
