package com.example.asm_android2.activity_ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.asm_android2.R;
import com.example.asm_android2.adapter.AdapterListLibrarian;

public class HomeAdmin extends AppCompatActivity {
     private AdapterListLibrarian listAccountThuThu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        ListView LV_librarianList=findViewById(R.id.LV_librarianList);
        listAccountThuThu=new AdapterListLibrarian(this);
        LV_librarianList.setAdapter(listAccountThuThu);
        Button BTN_addLibrarian=findViewById(R.id.BTN_addLibrarian);
        Button BTN_changeLibrarianPassword=findViewById(R.id.BTN_changeLibrarianPassword);
        BTN_addLibrarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(HomeAdmin.this, AddLibrarian.class);
                startActivityForResult(a,1);
            }
        });
        BTN_changeLibrarianPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(HomeAdmin.this, ChangeAdminPassword.class);
                startActivity(a);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==1){
                listAccountThuThu.notifyDataSetChanged();
            }
        }
    }
}