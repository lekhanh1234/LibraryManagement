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
import java.util.List;

import com.example.asm_android2.Account.AccountThuThuLogin;
import com.example.asm_android2.infoManageThuThu.Book;

public class DAOSach {
    private DATABASEThuvien dbThuVien;

    public DAOSach(DATABASEThuvien dbThuVien) {
        this.dbThuVien = dbThuVien;
    }

    public List<Book> getAllSach(){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from book where idthuthu="+AccountThuThuLogin.getId(),null);
        List<Book> list=new ArrayList<>();
        while (cursor.moveToNext()){
            String masach=cursor.getString(1);
            String tensach=cursor.getString(2);
            int giathue =cursor.getInt(3);
            int idloaisach=cursor.getInt(4);
            DAOLoaiSach daoLoaiSach=new DAOLoaiSach(dbThuVien);
            String nameCatoloryBook=daoLoaiSach.getNamebyId(idloaisach);
            list.add(new Book(masach,tensach,giathue,nameCatoloryBook,AccountThuThuLogin.getId()));
        }
        if(list.size()==0) return null;
        return list;
    }

    public boolean insertBook(Book book){
        SQLiteDatabase db=dbThuVien.getWritableDatabase();
        ContentValues values = new ContentValues();
        List<Book> list=getAllSach();
        if(list!=null) {
            for (Book x : list) {
                if (x.getMasach().equalsIgnoreCase(book.getMasach())) return false;
            }
        }

        DAOLoaiSach daoLoaiSach=new DAOLoaiSach(dbThuVien);
        int idloaisach=daoLoaiSach.getIdByName(book.getLoaiSach());
        values.put("idLoaisach",idloaisach);


        // gui du lieu len sever
        int indexId[]=new int[1];
       Thread a= new Thread(new Runnable() {
            @Override
            public void run() {
                indexId[0]=addBookToSever(book.getMasach(),book.getTenSach(),book.getGiaThue(),idloaisach,AccountThuThuLogin.getId());

            }
        });
        a.start();
        try {
            a.join();
        } catch (Exception e){}

        Log.d("id sach tra ve tu sever:"+indexId[0], "insertBook: ");
        values.put("id",indexId[0]);
        values.put("masach",book.getMasach());
        values.put("name",book.getTenSach());
        values.put("giathue",book.getGiaThue());
        values.put("idthuthu",AccountThuThuLogin.getId());
        db.insert("book", null, values);
        return true;
    }
    public int getIdByMaSach(String masach){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from book where masach='"+masach+"' and idthuthu="+AccountThuThuLogin.getId(),null);
        //dam bao ten loai sach chi co mot
        int id=-1;
        while (cursor.moveToNext())
            id=cursor.getInt(0);
        return id;
    }

    public int getGiaTriTheoMaSach(String masach){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from book where masach='"+masach+"' and idthuthu="+AccountThuThuLogin.getId(),null);
        //dam bao ten loai sach chi co mot
        int value=0;
        while (cursor.moveToNext())
            value=cursor.getInt(3);
        cursor.close();
        return value;
    }
    public String getNameById(int id){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from book where id="+id,null);
        //dam bao ten loai sach chi co mot
        String name=null;
        while (cursor.moveToNext())
            name=cursor.getString(2);
        cursor.close();
        return name;
    }
    public String getMaSachById(int id){
        SQLiteDatabase db=dbThuVien.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from book where id="+id,null);
        //dam bao ten loai sach chi co mot
        String name=null;
        while (cursor.moveToNext())
            name=cursor.getString(1);
        cursor.close();
        return name;
    }
    public void deleteBookById(int idBook){
        SQLiteDatabase db=dbThuVien.getWritableDatabase();
        db.delete("book","id = ? ",new String[]{idBook+""});
        // xoa tiep sach tren sever
        new Thread(new Runnable() {
            @Override
            public void run() {
                deleteBookFromSever(idBook);
            }
        }).start();
    }
    public int addBookToSever(String masach,String tensach,int giathue,int idLoaisach,int idthuthu){
        try{
            String Post_Url = "http://192.168.43.189:8080/sever_messenger/insertBook";
            URL urlSever_Post = new URL(Post_Url);
            String PARAMS = "masach="+masach+"&tensach=" +tensach+"&giathue="+giathue+"&idloaisach="+idLoaisach+"&idthuthu="+idthuthu;
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(PARAMS.getBytes());
            os.flush();
            httpURLConnection.getResponseCode();
            InputStream is=httpURLConnection.getInputStream();
            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
            return Integer.parseInt(bf.readLine());
        } catch (Exception e){
            Log.d("co loi xay ra", e.toString());
            return -10;
        }
    }
    public void deleteBookFromSever(int idBook){
        try{
            String Post_Url = "http://192.168.43.189:8080/sever_messenger/deleteBook?idbook="+idBook;
            URL urlSever_Post = new URL(Post_Url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.getResponseCode();
        } catch (Exception e){
        }
    }
    public boolean checkCatoloryBookById(int idCatolory){
        SQLiteDatabase database=dbThuVien.getWritableDatabase();
        Cursor cursor= database.rawQuery("select * from book where idLoaisach="+idCatolory,null);
        return cursor.moveToNext();
    }

}
