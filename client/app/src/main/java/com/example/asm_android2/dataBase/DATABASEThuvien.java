package com.example.asm_android2.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DATABASEThuvien extends SQLiteOpenHelper {

    public DATABASEThuvien(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d("hamkhoitao", "onCreate: ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("tao moi table", "onCreate: ");
        db.execSQL("create table accountThuthu(id INTEGER PRIMARY KEY , nameUser nvarchar(50) not null ,passWord nvarchar(50) not null,nameThuThu nvarchar(50) not null,dinhdanh TEXT not null)");
        db.execSQL("create table loaisach(id INTEGER primary key ,maloaisach TEXT not null,tenloaisach Text not null,idthuthu Integer not null,FOREIGN KEY(idthuthu) REFERENCES accountThuthu(id))");
        db.execSQL("create table book(id INTEGER primary key ,masach TEXT not null,name Text not null,giathue INTEGER not null,idLoaisach Integer not null,idthuthu Integer not null, FOREIGN KEY(idLoaisach) REFERENCES loaisach(id),FOREIGN KEY(idthuthu) REFERENCES accountThuthu(id))");
        db.execSQL("create table memberThuthu(id INTEGER primary key  ,madinhdanh TEXT not null,name Text not null,idthuthu Integer not null,foreign key(idthuthu) references accountThuthu(id))");
        db.execSQL("create table phieumuon(id INTEGER primary key ,maphieu TEXT not null,idthuthu INTEGER not null,idBook Integer not null,id_member Integer not null, " +
                "tinhtrang INTEGER not null, ngaythue date not null, thoihan date not null,FOREIGN KEY(idthuthu) REFERENCES accountThuthu(id),FOREIGN KEY(id_member) REFERENCES memberThuthu(id)," +
                "FOREIGN KEY(idBook) REFERENCES book(id))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
