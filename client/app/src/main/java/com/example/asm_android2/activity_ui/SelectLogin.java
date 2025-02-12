package com.example.asm_android2.activity_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asm_android2.R;

public class SelectLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);
        Button BTN_admin_login=findViewById(R.id.BTN_admin_login);
        Button BTN_thuthu_login=findViewById(R.id.BTN_thuthu_login);
        Button BTN_confirm=findViewById(R.id.BTN_confirm);
        int checkSelect[]=new int[1];
        checkSelect[0]=-1;
        BTN_admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTN_admin_login.setBackgroundColor(getResources().getColor(R.color.blue));
                BTN_thuthu_login.setBackgroundColor(getResources().getColor(R.color.grey));
                checkSelect[0]=0;
            }
        });

        BTN_thuthu_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTN_thuthu_login.setBackgroundColor(getResources().getColor(R.color.blue));
                BTN_admin_login.setBackgroundColor(getResources().getColor(R.color.grey));
                checkSelect[0]=1;
            }
        });

        BTN_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelect[0]==1){
                    Intent a=new Intent(SelectLogin.this, LibrarianLogin.class);
                    startActivity(a);
                }
                if(checkSelect[0]==0){
                    Intent a=new Intent(SelectLogin.this, LoginAdmin.class);
                    startActivity(a);
                }
            }
        });

    }
}