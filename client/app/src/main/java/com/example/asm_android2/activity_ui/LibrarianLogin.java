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
import com.example.asm_android2.ServerService.PrepareMethod;
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
                            String url = "http://192.168.43.189:8080/sever_messenger/checkloginthuthu";
                            String PARAMS = "usedname=" +edt_nameUser.getText().toString() + "&" + "password=" + edt_passWord.getText().toString();
                            HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection(url,"Post");
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
            LibraryDB dbLibrary = new LibraryDB(LibrarianLogin.this, "DATABASEThuVien", null, 1);
            SQLiteDatabase database = dbLibrary.getWritableDatabase();
            {
                int receivedNumber = dataInputStream.readInt();
                byte[] tableLibrarianResult = new byte[receivedNumber];
                is.read(tableLibrarianResult);
                String result = new String(tableLibrarianResult, StandardCharsets.UTF_8);
                String arrrayDetached[] = result.split(regex);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Integer.parseInt(arrrayDetached[0]));
                contentValues.put("nameUser", arrrayDetached[1]);
                contentValues.put("password", arrrayDetached[2]);
                contentValues.put("name", arrrayDetached[3]);
                contentValues.put("dinhdanh", arrrayDetached[4]);
                database.insert("librarian", null, contentValues);
                Librarian.setId(Integer.parseInt(arrrayDetached[0]));
            }
            while (true) {
                int recivednumber = dataInputStream.readInt();
                if (recivednumber == 0) break;
                byte[] byteCategoryTable = new byte[recivednumber];
                is.read(byteCategoryTable);
                String categoryTable = new String(byteCategoryTable, StandardCharsets.UTF_8);
                String valuecategoryTable[] = categoryTable.split(regex);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Integer.parseInt(valuecategoryTable[0]));
                contentValues.put("categoryCode", valuecategoryTable[1]);
                contentValues.put("categoryName", valuecategoryTable[2]);
                contentValues.put("idLibrarian", Integer.parseInt(valuecategoryTable[3]));
                database.insert("category", null, contentValues);
            }

            while (true) {
                int recivedNumberTableBook = dataInputStream.readInt();
                if (recivedNumberTableBook == 0) break;
                byte[] byteBookTable = new byte[recivedNumberTableBook];
                is.read(byteBookTable);
                String tableBook = new String(byteBookTable, StandardCharsets.UTF_8);
                String valuetableBook[] = tableBook.split(regex);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Integer.parseInt(valuetableBook[0]));
                contentValues.put("bookCode", valuetableBook[1]);
                contentValues.put("bookName", valuetableBook[2]);
                contentValues.put("price", Integer.parseInt(valuetableBook[3]));
                contentValues.put("idCategory", Integer.parseInt(valuetableBook[4]));
                contentValues.put("idLibrarian", Integer.parseInt(valuetableBook[5]));
                database.insert("book", null, contentValues);
            }
            while (true) {
                int recivedNumber = dataInputStream.readInt();
                if (recivedNumber == 0) break;
                byte[] byteMember = new byte[recivedNumber];
                is.read(byteMember);
                String result = new String(byteMember, StandardCharsets.UTF_8);
                String arrayKetqua[] = result.split(regex);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Integer.parseInt(arrayKetqua[0]));
                contentValues.put("dinhdanh", arrayKetqua[1]);
                contentValues.put("name", arrayKetqua[2]);
                contentValues.put("idLibrarian", Integer.parseInt(arrayKetqua[3]));
                database.insert("member", null, contentValues);
            }
            while (true) {
                int recivedNumber = dataInputStream.readInt();
                if (recivedNumber == 0) break;
                byte[] byteLoanSlipTable = new byte[recivedNumber];
                is.read(byteLoanSlipTable);
                String ketquatruyvansever = new String(byteLoanSlipTable, StandardCharsets.UTF_8);
                String arrayKetqua[] = ketquatruyvansever.split(regex);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Integer.parseInt(arrayKetqua[0]));
                contentValues.put("receiptNumber", arrayKetqua[1]);
                contentValues.put("idLibrarian", Integer.parseInt(arrayKetqua[2]));
                contentValues.put("idBook", Integer.parseInt(arrayKetqua[3]));
                contentValues.put("idMember", Integer.parseInt(arrayKetqua[4]));
                contentValues.put("states", Integer.parseInt(arrayKetqua[5]));
                contentValues.put("rentalDate", arrayKetqua[6]);
                contentValues.put("deadline", arrayKetqua[7]);
                database.insert("loanSlip", null, contentValues);
            }
            database.close();
            Intent a = new Intent(LibrarianLogin.this, Home.class);
            startActivity(a);
        }
        catch (Exception e){}
    }
}