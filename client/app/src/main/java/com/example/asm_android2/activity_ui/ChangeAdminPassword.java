package com.example.asm_android2.activity_ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_android2.R;
import com.example.asm_android2.modal.Admin;
import com.example.asm_android2.ServerService.NetworkUtils;
import com.example.asm_android2.ServerService.PrepareMethod;
import com.example.asm_android2.modal.LibrarianAdminControl;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class ChangeAdminPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word_thu_thu);
        EditText EDT_LibrarianDinhDanh=findViewById(R.id.EDT_LibrarianDinhDanh);
        EditText EDT_newPassWord=findViewById(R.id.EDT_newPassWord);
        EditText EDT_newPassWord2=findViewById(R.id.EDT_newPassWord2);
        Button BTN_confirmChange=findViewById(R.id.BTN_confirmChange);
        BTN_confirmChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a=new AlertDialog.Builder(ChangeAdminPassword.this);
                a.setTitle("Thay đổi mật khẩu");
                a.setMessage("Bạn muốn thay đổi mật khẩu thủ thư");
                a.setCancelable(true);
                a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(NetworkUtils.isNetworkAvailable(ChangeAdminPassword.this)==false){
                            Toast.makeText(ChangeAdminPassword.this,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String LibrarianDinhDanh=EDT_LibrarianDinhDanh.getText().toString().trim();
                        String newPassWord=EDT_newPassWord.getText().toString().trim();
                        String newPassWord2=EDT_newPassWord2.getText().toString().trim();
                        if(LibrarianDinhDanh.length()==0||newPassWord.length()==0||newPassWord2.length()==0){
                            Toast.makeText(ChangeAdminPassword.this,"Thông tin trống !",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<LibrarianAdminControl> list= Admin.getLibrarianAdminControlList();
                       for(int i=0;i<list.size();i++){
                           if(LibrarianDinhDanh.equals(list.get(i).getDinhdanh())) break;
                           if(i==list.size()-1){
                               Toast.makeText(ChangeAdminPassword.this,"Định danh thủ thư không tồn tại !",Toast.LENGTH_SHORT).show();
                               return;
                           }
                       }
                        if(newPassWord.equals(newPassWord2)==false){
                            Toast.makeText(ChangeAdminPassword.this,"Mật khẩu không khớp !",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(newPassWord.length()<5){
                            Toast.makeText(ChangeAdminPassword.this,"Mật khẩu phải ít nhất 5 kí tự !",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        HttpURLConnection connection= PrepareMethod.createMethodConnection("http://192.168.43.189:8080/sever_messenger/changePassWordAccountThuThu","Post");
                        try {
                            OutputStream os=connection.getOutputStream();
                            os.write(("newpassword="+newPassWord+"&dinhdanh="+LibrarianDinhDanh).getBytes());
                            os.flush();
                            connection.getResponseCode();
                        }catch (Exception e){}
                    }
                });
                a.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            }
        });

    }
}