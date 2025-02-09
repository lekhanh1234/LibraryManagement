package com.example.asm_android2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_android2.Account.AccountThuThuLogin;
import com.example.asm_android2.OperationSever.checkInternet;
import com.example.asm_android2.dataBase.DAOMember;
import com.example.asm_android2.dataBase.DAOPhieuMuon;
import com.example.asm_android2.dataBase.DAOSach;
import com.example.asm_android2.dataBase.DAOThuthu;
import com.example.asm_android2.dataBase.DATABASEThuvien;
import com.example.asm_android2.infoManageThuThu.Book;
import com.example.asm_android2.infoManageThuThu.Member;
import com.example.asm_android2.infoManageThuThu.Phieumuon;

import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addPhieu extends AppCompatActivity {
    private EditText EDT_maPhieu;
    private EditText EDT_nameMember;
    private EditText EDT_maThanhVien;
    private Spinner spinner_listBook;
    private EditText EDT_thoiHan;
    private Button BTN_selectTime;
    private Button BTN_themPhieuMoi;
    private String tenSachandMaSach="";
    private String[] listBook=null;
    private int YearCurrent;
    private int MonthCurrent;
    private int DayCurrent;
    private int YearSelect;
    private int MonthSelect;
    private int DaySelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phieu);
        EDT_maPhieu=findViewById(R.id.EDT_maPhieu);
        EDT_nameMember=findViewById(R.id.EDT_tenThanhVien);
        EDT_maThanhVien=findViewById(R.id.EDT_maThanhVien);
        spinner_listBook=findViewById(R.id.spinner_listBook);
        EDT_thoiHan=findViewById(R.id.EDT_thoiHan);
        BTN_selectTime=findViewById(R.id.BTN_selectTime);
        BTN_themPhieuMoi=findViewById(R.id.BTN_confirmAddPhieuMuon);
        getSupportActionBar().setTitle("Thêm phiếu mới");

        Calendar calendar = Calendar.getInstance();
        YearCurrent=calendar.get(Calendar.YEAR);
        MonthCurrent=calendar.get(Calendar.MONTH)+1;
        DayCurrent=calendar.get(Calendar.DAY_OF_MONTH);

        DAOSach daoSach=new DAOSach(new DATABASEThuvien(this,"DATABASEThuVien",null,1));
        List<Book> list=daoSach.getAllSach();
        if(list!=null){
            listBook=new String[list.size()];
            for(int i=0;i<list.size();i++){
                listBook[i]=list.get(i).getMasach()+" : "+list.get(i).getTenSach();
            }
        }
        else listBook=new String[]{""};

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listBook);
        spinner_listBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenSachandMaSach=((TextView)view).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_listBook.setAdapter(adapter);

        BTN_selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(addPhieu.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                YearSelect=year;
                                MonthSelect=month+1;
                                DaySelect=dayOfMonth;
                                EDT_thoiHan.setText(""+YearSelect+"-"+MonthSelect+"-"+DaySelect);
                            }
                        }, YearCurrent, MonthCurrent, DayCurrent);

                // Hiển thị hộp thoại DatePickerDialog
                datePickerDialog.show();
            }
        });



        BTN_themPhieuMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInternet.isNetworkAvailable(addPhieu.this)==false){
                    Toast.makeText(addPhieu.this,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }

                String maPhieu=EDT_maPhieu.getText().toString().trim();
                if(maPhieu.length()==0) {
                    Toast.makeText(addPhieu.this,"Ma Phieu Trong",Toast.LENGTH_SHORT).show();
                    return;
                }
                int idThuthu=AccountThuThuLogin.getId();
                String nameMember=EDT_nameMember.getText().toString().trim();
                if(nameMember.length()==0){
                    Toast.makeText(addPhieu.this,"Ten Thanh Vien Trong",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Pattern pattern=Pattern.compile("^[a-zA-Z]*(\\s[a-zA-Z]*)*$");
                    Matcher matcher=pattern.matcher(nameMember);
                    if(matcher.matches()==false){
                        Toast.makeText(addPhieu.this,"Ten Khong Hop Le",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                String maThanhVien=EDT_maThanhVien.getText().toString().trim();
                if(maThanhVien.length()==0){
                    Toast.makeText(addPhieu.this, "Ma thanh vien trong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tenSachandMaSach.length()==0) {
                    Toast.makeText(addPhieu.this, "Khong co sach duoc chon !", Toast.LENGTH_SHORT).show();
                    return;
                }
                String arrayMaSachandTenSach[]=tenSachandMaSach.split(":");
                String MaSach=arrayMaSachandTenSach[0].trim();
                String TenSach=arrayMaSachandTenSach[1].trim();

                int trangthai=0;
                String Currenttime=""+YearCurrent+"-"+MonthCurrent+"-"+DayCurrent;
                    if(YearSelect==0&&MonthSelect==0&&DaySelect==0) {
                        Toast.makeText(addPhieu.this, "Chon thoi han", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String selectTime=""+YearSelect+"-"+MonthSelect+"-"+DaySelect;
               if(YearSelect<YearCurrent){
                   Toast.makeText(addPhieu.this, "Thoi han khong hop le", Toast.LENGTH_SHORT).show();
                   return;
               }
               if(YearSelect==YearCurrent) {
                   if (MonthSelect < MonthCurrent) {
                       {
                           Toast.makeText(addPhieu.this, "Thoi han khong hop le", Toast.LENGTH_SHORT).show();
                           return;
                       }
                   }
                   if (MonthSelect == MonthCurrent) {
                       if (DaySelect <= DayCurrent) {
                           {
                               Toast.makeText(addPhieu.this, "Thoi han khong hop le", Toast.LENGTH_SHORT).show();
                               return;
                           }
                       }
                   }
               }

                DATABASEThuvien newdb=new DATABASEThuvien(addPhieu.this,"DATABASEThuVien",null,1);
                DAOPhieuMuon daoPhieuMuon=new DAOPhieuMuon(newdb);
                List<Phieumuon> list=daoPhieuMuon.getAllPhieuMuon();
                if(list!=null)
                    for(Phieumuon x:list){
                        if(x.getMaphieu().equalsIgnoreCase(maPhieu)){
                            Toast.makeText(addPhieu.this,"Mã phiếu đã tồn tại",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                Phieumuon phieumuon=new Phieumuon(maPhieu,idThuthu,TenSach,MaSach,nameMember,maThanhVien,trangthai,Currenttime,selectTime);
                daoPhieuMuon.insertPhieumuon(phieumuon);
                newdb.getWritableDatabase().close();
                newdb.getReadableDatabase().close();
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