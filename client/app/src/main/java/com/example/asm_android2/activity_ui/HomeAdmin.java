package com.example.asm_android2.activity_ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.asm_android2.R;
import com.example.asm_android2.adapter.adtListAccountThuThu;

public class HomeAdmin extends AppCompatActivity {
     private adtListAccountThuThu listAccountThuThu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_main_admin);
        ListView LV_listThuthu=findViewById(R.id.LV_listThuthu);
        listAccountThuThu=new adtListAccountThuThu(this);
        LV_listThuthu.setAdapter(listAccountThuThu);
        Button BTN_addThuthu=findViewById(R.id.BTN_addThuthu);
        Button BTN_changePassWordThuThu=findViewById(R.id.BTN_changePassWordThuThu);
        BTN_addThuthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(HomeAdmin.this, AddLibrarian.class);
                startActivityForResult(a,1);
            }
        });
        BTN_changePassWordThuThu.setOnClickListener(new View.OnClickListener() {
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