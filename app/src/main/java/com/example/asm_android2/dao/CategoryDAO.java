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
import com.example.asm_android2.modal.Category;

public class CategoryDAO {
    private LibraryDB dbLibrary;
    public CategoryDAO(LibraryDB dbLibrary) {
        this.dbLibrary = dbLibrary;
    }

    public List<Category> getAllCategory(){
        SQLiteDatabase db=this.dbLibrary.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from category where idLibrarian="+ Librarian.getId(),null);
        List<Category> list=new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String categoryCode=cursor.getString(1);
            String name=cursor.getString(2);
            list.add(new Category(id,categoryCode,name, Librarian.getId()));
        }
        cursor.close();
        return list;
    }
    public boolean insertCategory(Category category){
        List<Category> list= getAllCategory();
        for(Category x:list){
            if(x.getCategoryName().equalsIgnoreCase(category.getCategoryName())) return false;
            if(x.getCategoryCode().equalsIgnoreCase(category.getCategoryCode())) return false;
        }
        SQLiteDatabase db=dbLibrary.getWritableDatabase();
        int idtrave[]=new int[1];
        Thread InsertCategorySever=new Thread(new Runnable() {
            @Override
            public void run() {
              idtrave[0]=InsertCategoryToSever(category.getCategoryCode(),category.getCategoryName(), Librarian.getId());
            }
        });
        InsertCategorySever.start();
        try{
            InsertCategorySever.join();
        } catch (Exception e){}
        ContentValues content=new ContentValues();
        content.put("id",idtrave[0]);
        content.put("categoryCode",category.getCategoryCode());
        content.put("categoryName",category.getCategoryName());
        content.put("idLibrarian", Librarian.getId());
        db.insert("category",null,content);
        return true;
    }
    public int amountCategory(int id){
        SQLiteDatabase db=dbLibrary.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from book where idCategory ="+id+" and idLibrarian="+ Librarian.getId(),null);
        int amount=0;
        while (cursor.moveToNext()) amount++;
        cursor.close();
        db.close();
        return amount;
    }
    public int getIdByName(String name){
        SQLiteDatabase db=dbLibrary.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from category where categoryName ='"+name+"' and idLibrarian= " + Librarian.getId(),null);
        //dam bao categoryName là duy nhất
        int id=-1;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close(); db.close();
        return id;
    }
    public void deleteCategoryById(int id){
        SQLiteDatabase db=dbLibrary.getWritableDatabase();
        db.delete("category","id = ? ",new String[]{id+""});
        // xoa tiep sach tren sever
        new Thread(new Runnable() {
            @Override
            public void run() {
                deleteCategoryOnSever(id);
            }
        }).start();
    }
    public String getNamebyId(int idCategory){
        SQLiteDatabase db=dbLibrary.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from category where id ="+idCategory+" and idLibrarian="+ Librarian.getId(),null);
        // dam bao ten loai sach chi co mot
        String name=null;
        if(cursor.moveToNext()) {
            Log.d("idCategory:"+idCategory, "getNamebyId: ");
            name = cursor.getString(2);
        }
        cursor.close(); db.close();
        return name;
    }
    public int InsertCategoryToSever(String macategory,String tenCategory,int idLibrarian){
        try{
            String url = "http://192.168.43.189:8080/sever_messenger/insertCatoloryBook";
            String PARAMS = "macategory="+macategory+"&categoryName=" +tenCategory+"&idLibrarian="+idLibrarian;
            HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(url,"POST");
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(PARAMS.getBytes());
            os.flush();
            httpURLConnection.getResponseCode();
            InputStream is=httpURLConnection.getInputStream();
            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
            return Integer.parseInt(bf.readLine()); // trả về id;
        } catch (Exception e){
            return -1;
        }
    }
    public void deleteCategoryOnSever(int id){
        try{
            String url = "http://192.168.43.189:8080/sever_messenger/deleteLoaiBook?idCategory="+id;
            HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(url,"DELETE");
            httpURLConnection.getResponseCode();
        } catch (Exception e){
        }
    }
}
