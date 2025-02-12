package com.example.asm_android2.activity_ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_android2.R;
import com.example.asm_android2.modal.Librarian;
import com.example.asm_android2.ServerService.NetworkUtils;
import com.example.asm_android2.dao.LibraryDB;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LibrarianLogin extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText edt_nameUser=findViewById(R.id.edt_nameUser);
        EditText edt_passWord=findViewById(R.id.edt_passWord);
        Button btn_loGin=findViewById(R.id.btn_loGin);
        btn_loGin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtils.isNetworkAvailable(LibrarianLogin.this)==false){
                    Toast.makeText(LibrarianLogin.this,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }
                Thread a=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String Post_Url = "http://192.168.43.189:8080/sever_messenger/checkloginthuthu";
                            URL urlSever_Post = null;
                            urlSever_Post = new URL(Post_Url);
                            String PARAMS = "usedname=" +edt_nameUser.getText().toString() + "&" + "password=" + edt_passWord.getText().toString();
                            HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                            httpURLConnection.setDoOutput(true);
                            OutputStream os = httpURLConnection.getOutputStream();
                            os.write(PARAMS.getBytes());
                            Thread.sleep(300);
                            os.flush();
                            int Responsecode = httpURLConnection.getResponseCode();
                            if(Responsecode!=200){
                             handler.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      AlertDialog.Builder builder= new AlertDialog.Builder(LibrarianLogin.this);
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
                            else{
                                getData(httpURLConnection.getInputStream());
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
                a.start();
            }
        });
    }
    private void getData(InputStream is){
        try {
            LibrarianLogin.this.deleteDatabase("DATABASEThuVien");
            DataInputStream dataInputStream = new DataInputStream(is);
            String regex = "abcxyz987";
            LibraryDB dbThuVien = new LibraryDB(LibrarianLogin.this, "DATABASEThuVien", null, 1);
            SQLiteDatabase database = dbThuVien.getWritableDatabase();
            {
                int receivedNumber = dataInputStream.readInt();
                byte[] ketquatableThuthu = new byte[receivedNumber];
                is.read(ketquatableThuthu);
                String ketquatruyvan = new String(ketquatableThuthu, StandardCharsets.UTF_8);
                String arrrayDetached[] = ketquatruyvan.split(regex);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Integer.parseInt(arrrayDetached[0]));
                contentValues.put("nameUser", arrrayDetached[1]);
                contentValues.put("passWord", arrrayDetached[2]);
                contentValues.put("nameThuThu", arrrayDetached[3]);
                contentValues.put("dinhdanh", arrrayDetached[4]);
                database.insert("accountThuthu", null, contentValues);
                Librarian.setId(Integer.parseInt(arrrayDetached[0]));
            }
            while (true) {
                int recivednumber = dataInputStream.readInt();
                if (recivednumber == 0) break;
                byte[] byteTableLoaiSach = new byte[recivednumber];
                is.read(byteTableLoaiSach);
                //thuc hien them vao co so du lieu/ bang loai sach
                String tableLoaiBook = new String(byteTableLoaiSach, StandardCharsets.UTF_8);
                String valuetableLoaiBook[] = tableLoaiBook.split(regex);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Integer.parseInt(valuetableLoaiBook[0]));
                contentValues.put("maloaisach", valuetableLoaiBook[1]);
                contentValues.put("tenloaisach", valuetableLoaiBook[2]);
                contentValues.put("idthuthu", Integer.parseInt(valuetableLoaiBook[3]));
                database.insert("loaisach", null, contentValues);
            }

            while (true) {
                int recivedNumberTableBook = dataInputStream.readInt();
                if (recivedNumberTableBook == 0) break;
                byte[] byteTableSach = new byte[recivedNumberTableBook];
                is.read(byteTableSach);
                String tableBook = new String(byteTableSach, StandardCharsets.UTF_8);
                String valuetableBook[] = tableBook.split(regex);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Integer.parseInt(valuetableBook[0]));
                contentValues.put("masach", valuetableBook[1]);
                contentValues.put("name", valuetableBook[2]);
                contentValues.put("giathue", Integer.parseInt(valuetableBook[3]));
                contentValues.put("idLoaisach", Integer.parseInt(valuetableBook[4]));
                contentValues.put("idthuthu", Integer.parseInt(valuetableBook[5]));
                database.insert("book", null, contentValues);
            }
            while (true) {
                int recivedNumber = dataInputStream.readInt();
                if (recivedNumber == 0) break;
                byte[] byteMember = new byte[recivedNumber];
                is.read(byteMember);
                String ketquatruyvansever = new String(byteMember, StandardCharsets.UTF_8);
                String arrayKetqua[] = ketquatruyvansever.split(regex);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Integer.parseInt(arrayKetqua[0]));
                contentValues.put("madinhdanh", arrayKetqua[1]);
                contentValues.put("name", arrayKetqua[2]);
                contentValues.put("idthuthu", Integer.parseInt(arrayKetqua[3]));
                database.insert("memberThuthu", null, contentValues);
            }
            while (true) {
                int recivedNumber = dataInputStream.readInt();
                if (recivedNumber == 0) break;
                byte[] bytePhieumuon = new byte[recivedNumber];
                is.read(bytePhieumuon);
                String ketquatruyvansever = new String(bytePhieumuon, StandardCharsets.UTF_8);
                String arrayKetqua[] = ketquatruyvansever.split(regex);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Integer.parseInt(arrayKetqua[0]));
                contentValues.put("maphieu", arrayKetqua[1]);
                contentValues.put("idthuthu", Integer.parseInt(arrayKetqua[2]));
                contentValues.put("idBook", Integer.parseInt(arrayKetqua[3]));
                contentValues.put("id_member", Integer.parseInt(arrayKetqua[4]));
                contentValues.put("tinhtrang", Integer.parseInt(arrayKetqua[5]));
                contentValues.put("ngaythue", arrayKetqua[6]);
                contentValues.put("thoihan", arrayKetqua[7]);
                database.insert("phieumuon", null, contentValues);
            }
            database.close();
            Intent a = new Intent(LibrarianLogin.this, Home.class);
            startActivity(a);
        }
        catch (Exception e){}
    }
}