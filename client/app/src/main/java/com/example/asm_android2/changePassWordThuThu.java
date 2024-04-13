package com.example.asm_android2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_android2.Account.AccountAdminLogin;
import com.example.asm_android2.Account.AccountThuthu;
import com.example.asm_android2.OperationSever.checkInternet;
import com.example.asm_android2.OperationSever.prepareMessengerPost;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class changePassWordThuThu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word_thu_thu);
        EditText EDT_dinhdanhThuthu=findViewById(R.id.EDT_dinhdanhThuthu);
        EditText EDT_newPassWord=findViewById(R.id.EDT_newPassWord);
        EditText EDT_newPassWord2=findViewById(R.id.EDT_newPassWord2);
        Button BTN_confirmChangeAddThuThu=findViewById(R.id.BTN_confirmChangeAddThuThu);
        BTN_confirmChangeAddThuThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a=new AlertDialog.Builder(changePassWordThuThu.this);
                a.setTitle("Thay đổi mật khẩu");
                a.setMessage("Bạn muốn thay đổi mật khẩu thủ thư");
                a.setCancelable(true);
                a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(checkInternet.isNetworkAvailable(changePassWordThuThu.this)==false){
                            Toast.makeText(changePassWordThuThu.this,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String dinhdanhThuthu=EDT_dinhdanhThuthu.getText().toString().trim();
                        String newPassWord=EDT_newPassWord.getText().toString().trim();
                        String newPassWord2=EDT_newPassWord2.getText().toString().trim();
                        if(dinhdanhThuthu.length()==0||newPassWord.length()==0||newPassWord2.length()==0){
                            Toast.makeText(changePassWordThuThu.this,"Thông tin trống !",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<AccountThuthu> list=AccountAdminLogin.getListAccountThuThu();
                       for(int i=0;i<list.size();i++){
                           if(dinhdanhThuthu.equals(list.get(i).getMadinhdanh())) break;
                           if(i==list.size()-1){
                               Toast.makeText(changePassWordThuThu.this,"Định danh thủ thư không tồn tại !",Toast.LENGTH_SHORT).show();
                               return;
                           }
                           // thu thu ton tai

                       }
                        if(newPassWord.equals(newPassWord2)==false){
                            Toast.makeText(changePassWordThuThu.this,"Mật khẩu không khớp !",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(newPassWord.length()<5){
                            Toast.makeText(changePassWordThuThu.this,"Mật khẩu phải ít nhất 5 kí tự !",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        HttpURLConnection connection= prepareMessengerPost.prepareMessenger("http://192.168.43.189:8080/sever_messenger/changePassWordAccountThuThu");
                        try {
                            OutputStream os=connection.getOutputStream();
                            os.write(("newpassword="+newPassWord+"&dinhdanh="+dinhdanhThuthu).getBytes());
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