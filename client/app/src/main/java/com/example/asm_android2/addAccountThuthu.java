package com.example.asm_android2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_android2.Account.AccountAdminLogin;
import com.example.asm_android2.Account.AccountThuthu;
import com.example.asm_android2.OperationSever.checkInternet;
import com.example.asm_android2.OperationSever.insertDataToSever;

import java.util.List;

public class addAccountThuthu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_thuthu);
        ActionBar a=getSupportActionBar();
        a.setTitle("Thêm Thủ Thư");
        a.setDisplayHomeAsUpEnabled(true);
        EditText EDT_nameThuThu=findViewById(R.id.EDT_nameThuThu);
        EditText EDT_Username_thuthu=findViewById(R.id.EDT_Username_thuthu);
        EditText EDT_passWord=findViewById(R.id.EDT_passWord);
        EditText EDT_dinhDanh=findViewById(R.id.EDT_dinhDanh);
        Button BTN_confirmAddThuThu=findViewById(R.id.BTN_confirmAddThuThu);
        BTN_confirmAddThuThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInternet.isNetworkAvailable(addAccountThuthu.this)==false){
                    Toast.makeText(addAccountThuthu.this,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }
                String name=EDT_nameThuThu.getText().toString().trim();
                String userName=EDT_Username_thuthu.getText().toString().trim();
                String passWord=EDT_passWord.getText().toString().trim();
                String dinhdanh=EDT_dinhDanh.getText().toString().trim();
                if(name.length()==0){
                    Toast.makeText(addAccountThuthu.this,"TÊN KHÔNG HỢP LỆ",Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i=0;i<name.length();i++){
                    if((name.charAt(i)<'A'||(name.charAt(i)>'Z'&&name.charAt(i)<'a'))&&name.codePointAt(i)!=32){
                        Toast.makeText(addAccountThuthu.this,"TÊN KHÔNG HỢP LỆ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(userName.length()<5){
                    Toast.makeText(addAccountThuthu.this,"USER NAME PHẢI CHỨA ÍT NHẤT 5 KÍ TỰ",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passWord.length()<5){
                    Toast.makeText(addAccountThuthu.this,"PASSWORD PHẢI CHỨA ÍT NHẤT 5 KÍ TỰ",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dinhdanh.length()<3){
                    Toast.makeText(addAccountThuthu.this,"DINH DANH TRỐNG PHẢI CHỨA ÍT NHẤT 3 KÍ TỰ",Toast.LENGTH_SHORT).show();
                    return;
                }

                // KIEM TRA DINH DANH VOI CAC TAI KHOAN THU THU CON LAI\
                List<AccountThuthu> list=AccountAdminLogin.getListAccountThuThu();
                for(AccountThuthu x: list){
                    if(x.getMadinhdanh().equalsIgnoreCase(dinhdanh)){
                        Toast.makeText(addAccountThuthu.this,"MÃ ĐỊNH DANH THỦ THƯ TRÙNG LẶP",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // TIENS HÀNH CẬP NHẬT DƯ LIEU TREN SEVER
                boolean result=new insertDataToSever().insertAccountThuThuToSever(name,userName,passWord,dinhdanh);
                if(result==false){
                    Toast.makeText(addAccountThuthu.this,"MÃ ĐỊNH DANH THỦ THƯ ĐÃ TỒN TẠI",Toast.LENGTH_SHORT).show();
                }
                setResult(1,new Intent());
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}