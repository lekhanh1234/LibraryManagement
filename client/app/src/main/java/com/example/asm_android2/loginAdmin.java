package com.example.asm_android2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_android2.Account.AccountAdminLogin;
import com.example.asm_android2.Account.AccountThuThuLogin;
import com.example.asm_android2.Account.AccountThuthu;
import com.example.asm_android2.OperationSever.checkAccountAdmin;
import com.example.asm_android2.OperationSever.checkInternet;
import com.example.asm_android2.dataBase.DATABASEThuvien;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class loginAdmin extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        EditText edt_nameUser=findViewById(R.id.edt_nameUser);
        EditText edt_passWord=findViewById(R.id.edt_passWord);
        Button btn_loGin=findViewById(R.id.btn_loGin);
        btn_loGin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInternet.isNetworkAvailable(loginAdmin.this)==false){
                    Toast.makeText(loginAdmin.this,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }
                String userName=edt_nameUser.getText().toString();
                String passWord=edt_passWord.getText().toString();
                if(userName.length()==0||passWord.length()==0){
                    Toast.makeText(loginAdmin.this,"BẠN CHƯA NHẬP ĐỦ THÔNG TIN",Toast.LENGTH_SHORT).show();
                    return;
                }
                Thread a=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loginAccountAdmin(userName,passWord);
                    }
                });
                a.start();
            }
        });
    }
    public void loginAccountAdmin(String userName,String passWord){
        try{
            HttpURLConnection httpURLConnection =new checkAccountAdmin().PrepareMessage(userName,passWord);
            int resultFromSever=httpURLConnection.getResponseCode();
            if(resultFromSever==404) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder= new AlertDialog.Builder(loginAdmin.this);
                        builder.setTitle("");
                        builder.setMessage("UserName hoặc Password không hợp lệ");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                });
            }
            else {
                InputStream is=httpURLConnection.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(is);
                String regex="abcxyz987";
                {
                    int receivedNumber = dataInputStream.readInt();
                    byte[] resuftAccount = new byte[receivedNumber];
                    is.read(resuftAccount);
                    String ketquatruyvan = new String(resuftAccount,StandardCharsets.UTF_8);
                    String arrrayDetached[] = ketquatruyvan.split(regex);
                    AccountAdminLogin.setId(Integer.parseInt(arrrayDetached[0]));
                    AccountAdminLogin.setUserName(arrrayDetached[1]);
                    AccountAdminLogin.setPassWord(arrrayDetached[2]);
                    AccountAdminLogin.setNameAdmin(arrrayDetached[3]);
                    AccountAdminLogin.setDinhdanh(arrrayDetached[4]);
                }

                ArrayList<AccountThuthu> list=new ArrayList<>();
                while (true) {
                    int recivednumber = dataInputStream.readInt();
                    if(recivednumber==0) break;
                    byte[] byteTableThuThu=new byte[recivednumber];
                    is.read(byteTableThuThu);
                    String tableThuThu=new String(byteTableThuThu,StandardCharsets.UTF_8);
                    String valuetableThuThu[]=tableThuThu.split(regex);
                    int idthuthu=Integer.parseInt(valuetableThuThu[0]);
                    String username=valuetableThuThu[1];
                    String password=valuetableThuThu[2];
                    String nameThuthu=valuetableThuThu[3];
                    String madinhdanh=valuetableThuThu[4];
                    list.add(new AccountThuthu(idthuthu,username,password,nameThuthu,madinhdanh));

                }
                AccountAdminLogin.setListAccountThuThu(list);
                Intent x=new Intent(loginAdmin.this, from_mainAdmin.class);
                startActivity(x);
            }
        } catch (Exception e){
        }
    }
}