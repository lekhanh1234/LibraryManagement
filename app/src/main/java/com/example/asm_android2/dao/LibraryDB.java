package com.example.asm_android2.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class LibraryDB extends SQLiteOpenHelper {

    public LibraryDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("tao moi table", "onCreate: ");
        db.execSQL("create table librarian(id INTEGER PRIMARY KEY , userName nvarchar(50) not null ,password nvarchar(50) not null,name nvarchar(50) not null,dinhdanh TEXT not null)");
        db.execSQL("create table category(id INTEGER primary key ,categoryCode TEXT not null,categoryName Text not null,idLibrarian Integer not null,FOREIGN KEY(idLibrarian) REFERENCES librarian(id))");
        db.execSQL("create table book(id INTEGER primary key ,bookCode TEXT not null,bookName Text not null,price INTEGER not null,idCategory Integer not null,idLibrarian Integer not null, FOREIGN KEY(idCategory) REFERENCES category(id),FOREIGN KEY(idLibrarian) REFERENCES librarian(id))");
        db.execSQL("create table member(id INTEGER primary key  ,dinhdanh TEXT not null,name Text not null,idLibrarian Integer not null,foreign key(idLibrarian) references librarian(id))");
        db.execSQL("create table loanSlip(id INTEGER primary key ,receiptNumber TEXT not null,idLibrarian INTEGER not null,idBook Integer not null,idMember Integer not null, " +
                "states INTEGER not null, rentalDate date not null, deadline date not null,FOREIGN KEY(idLibrarian) REFERENCES librarian(id),FOREIGN KEY(idMember) REFERENCES Member(id)," +
                "FOREIGN KEY(idBook) REFERENCES book(id))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
