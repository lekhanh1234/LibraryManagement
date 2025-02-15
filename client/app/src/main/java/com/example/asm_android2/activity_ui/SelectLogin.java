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
        Button BTN_loginAdmin=findViewById(R.id.BTN_loginAdmin);
        Button BTN_loginLibrarian=findViewById(R.id.BTN_loginLibrarian);
        Button BTN_confirm=findViewById(R.id.BTN_confirm);
        int checkSelect[]=new int[1];
        checkSelect[0]=-1;
        BTN_loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTN_loginAdmin.setBackgroundColor(getResources().getColor(R.color.blue));
                BTN_loginLibrarian.setBackgroundColor(getResources().getColor(R.color.grey));
                checkSelect[0]=0;
            }
        });

        BTN_loginLibrarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTN_loginLibrarian.setBackgroundColor(getResources().getColor(R.color.blue));
                BTN_loginAdmin.setBackgroundColor(getResources().getColor(R.color.grey));
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