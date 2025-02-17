package com.example.asm_android2.activity_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_android2.R;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EditText edt_user=findViewById(R.id.edt_user);
        EditText edt_password=findViewById(R.id.edt_password);
        EditText edt_LibrarianName=findViewById(R.id.EDT_LibrarianName);
        EditText edt_dinhdanh=findViewById(R.id.EDT_IDdinhdanh);
        Button btn_signUp=findViewById(R.id.btn_signUp);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Đăng kí tài khoản");
        btn_signUp.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                String nameUser=edt_user.getText().toString();
                String passWord=edt_password.getText().toString();
                String librarianName=edt_LibrarianName.getText().toString();
                String dinhdanh=edt_dinhdanh.getText().toString();
                if(nameUser.length()==0||passWord.length()==0)
                {
                    Toast.makeText(SignUp.this,"Error!\nTrường dữ liệu trống",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(SignUp.this,"Tài khoản đã tồn tại",Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}