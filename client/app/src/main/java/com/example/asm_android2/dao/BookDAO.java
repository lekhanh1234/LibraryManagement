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
import com.example.asm_android2.modal.Book;

public class BookDAO {
    private LibraryDB libraryDB;
    public BookDAO(LibraryDB libraryDB) {
        this.libraryDB = libraryDB;
    }
    public List<Book> getAllBook(){
        SQLiteDatabase db=libraryDB.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from book where idLibrarian ="+ Librarian.getId(),null);
        List<Book> list=new ArrayList<>();
        while (cursor.moveToNext()){
            String bookCode=cursor.getString(1);
            String bookName=cursor.getString(2);
            int price =cursor.getInt(3);
            int idCategory=cursor.getInt(4);
            list.add(new Book(bookCode,bookName,price,idCategory, Librarian.getId()));
        }
        if (db != null && db.isOpen()) {
            db.close();
        }
        if(list.size()==0) return null;
        return list;
    }

    public boolean insertBook(Book book){
        SQLiteDatabase db=libraryDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        List<Book> list= getAllBook();
        if(list!=null) {
            for (Book x : list) {
                if (x.getBookCode().equalsIgnoreCase(book.getBookCode())) return false;
            }
        }
       int indexId[]=new int[1];
       Thread a= new Thread(new Runnable() {
            @Override
            public void run() {
                indexId[0]=addBookToSever(book.getBookCode(),book.getBookName(),book.getPrice(),book.getIdCategory(), Librarian.getId());
            }
        });
        a.start();
        try {
            a.join();
        } catch (Exception e){}
        Log.d("id book from server :"+indexId[0], "insertBook: ");
        values.put("id",indexId[0]);
        values.put("bookCode",book.getBookCode());
        values.put("bookName",book.getBookName());
        values.put("price",book.getPrice());
        values.put("idCategory",book.getIdCategory());
        values.put("idLibrarian", Librarian.getId());
        db.insert("book", null, values);
        return true;
    }
    public int getIdByBookCode(String bookCode){
        SQLiteDatabase db=libraryDB.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from book where bookCode='"+bookCode+"' and idLibrarian="+ Librarian.getId(),null);
        int id= -1;
        while (cursor.moveToNext())
            id=cursor.getInt(0);
        return id;
    }

    public int getPriceByBookCode(String bookCode){
        SQLiteDatabase db=libraryDB.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from book where bookCode='"+bookCode+"' and idLibrarian="+ Librarian.getId(),null);
        //dam bao ten loai sach chi co mot
        int value=0;
        while (cursor.moveToNext())
            value=cursor.getInt(3);
        cursor.close();
        return value;
    }
    public String getNameById(int id){
        SQLiteDatabase db=libraryDB.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from book where id="+id,null);
        String name=null;
        while (cursor.moveToNext())
            name=cursor.getString(2);
        cursor.close();
        return name;
    }
    public String getBookCodeById(int id){
        SQLiteDatabase db=libraryDB.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from book where id="+id,null);
        String name=null;
        while (cursor.moveToNext())
            name=cursor.getString(1);
        cursor.close();
        return name;
    }
    public void deleteBookById(int idBook){
        SQLiteDatabase db=libraryDB.getWritableDatabase();
        db.delete("book","id = ? ",new String[]{idBook+""});
        db.close();
        new Thread(new Runnable() {
            @Override
            public void run() {
                deleteBookFromSever(idBook);
            }
        }).start();
    }

    public int addBookToSever(String bookCode,String bookName,int price,int idCategory,int idLibrarian){
        try{
            String PARAMS = "bookCode="+bookCode+"&bookName=" +bookName+"&price="+price+"&idCategory="+idCategory+"&idLibrarian="+idLibrarian;
            HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection("http://192.168.43.189:8080/sever_messenger/insertBook","POST");
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(PARAMS.getBytes());
            os.flush();
            httpURLConnection.getResponseCode();
            InputStream is=httpURLConnection.getInputStream();
            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
            return Integer.parseInt(bf.readLine());
        } catch (Exception e){
            Log.d("co loi xay ra", e.toString());
            return -1;
        }
    }
    public void deleteBookFromSever(int idBook){
        try{
            String url = "http://192.168.43.189:8080/sever_messenger/deleteBook?idbook="+idBook;
            HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(url,"Delete");
            httpURLConnection.getResponseCode();
        } catch (Exception e){
        }
    }
    public boolean checkCategoryBookById(int idCatolory){
        SQLiteDatabase database=libraryDB.getWritableDatabase();
        Cursor cursor= database.rawQuery("select * from book where idCategory="+idCatolory,null);
        cursor.close(); database.close();
        return cursor.moveToNext();
    }

}
