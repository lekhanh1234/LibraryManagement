package com.example.asm_android2.activity_ui;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.asm_android2.R;
import com.example.asm_android2.modal.Admin;
import com.example.asm_android2.ServerService.CheckAccountAdmin;
import com.example.asm_android2.ServerService.NetworkUtils;
import com.example.asm_android2.modal.LibrarianAdminControl;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LoginAdmin extends AppCompatActivity {
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
                if(NetworkUtils.isNetworkAvailable(LoginAdmin.this)==false){
                    Toast.makeText(LoginAdmin.this,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }
                String userName=edt_nameUser.getText().toString();
                String passWord=edt_passWord.getText().toString();
                if(userName.length()==0||passWord.length()==0){
                    Toast.makeText(LoginAdmin.this,"BẠN CHƯA NHẬP ĐỦ THÔNG TIN",Toast.LENGTH_SHORT).show();
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
            HttpURLConnection httpURLConnection =new CheckAccountAdmin().PrepareMessage(userName,passWord);
            int resultFromSever=httpURLConnection.getResponseCode();
            if(resultFromSever==404) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder= new AlertDialog.Builder(LoginAdmin.this);
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
                    Admin.setId(Integer.parseInt(arrrayDetached[0]));
                    Admin.setUserName(arrrayDetached[1]);
                    Admin.setPassword(arrrayDetached[2]);
                    Admin.setName(arrrayDetached[3]);
                    Admin.setDinhdanh(arrrayDetached[4]);
                }

                ArrayList<LibrarianAdminControl> list=new ArrayList<>();
                while (true) {
                    int recivednumber = dataInputStream.readInt();
                    if(recivednumber==0) break;
                    byte[] byteTableThuThu=new byte[recivednumber];
                    is.read(byteTableThuThu);
                    String librarianTable=new String(byteTableThuThu,StandardCharsets.UTF_8);
                    String valuelibrarianTable[]=librarianTable.split(regex);
                    int idLibrarian=Integer.parseInt(valuelibrarianTable[0]);
                    String username=valuelibrarianTable[1];
                    String password=valuelibrarianTable[2];
                    String nameThuthu=valuelibrarianTable[3];
                    String madinhdanh=valuelibrarianTable[4];
                    list.add(new LibrarianAdminControl(idLibrarian,username,password,nameThuthu,madinhdanh));

                }
                Admin.setLibrarianAdminControlList(list);
                Intent x=new Intent(LoginAdmin.this, HomeAdmin.class);
                startActivity(x);
            }
        } catch (Exception e){
        }
    }
}