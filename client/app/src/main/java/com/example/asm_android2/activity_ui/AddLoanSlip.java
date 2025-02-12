package com.example.asm_android2.activity_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
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

import com.example.asm_android2.R;
import com.example.asm_android2.modal.Librarian;
import com.example.asm_android2.ServerService.NetworkUtils;
import com.example.asm_android2.dao.LoanSlipDAO;
import com.example.asm_android2.dao.BookDAO;
import com.example.asm_android2.dao.LibraryDB;
import com.example.asm_android2.modal.Book;
import com.example.asm_android2.modal.LoanSlip;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddLoanSlip extends AppCompatActivity {
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
        MonthCurrent=calendar.get(Calendar.MONTH) + 1;
        DayCurrent=calendar.get(Calendar.DAY_OF_MONTH);

        BookDAO bookDao =new BookDAO(new LibraryDB(this,"DATABASEThuVien",null,1));
        List<Book> list= bookDao.getAllBook();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddLoanSlip.this,
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
                if(NetworkUtils.isNetworkAvailable(AddLoanSlip.this)==false){
                    Toast.makeText(AddLoanSlip.this,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }

                String maPhieu=EDT_maPhieu.getText().toString().trim();
                if(maPhieu.length()==0) {
                    Toast.makeText(AddLoanSlip.this,"Ma Phieu Trong",Toast.LENGTH_SHORT).show();
                    return;
                }
                int idThuthu= Librarian.getId();
                String nameMember=EDT_nameMember.getText().toString().trim();
                if(nameMember.length()==0){
                    Toast.makeText(AddLoanSlip.this,"Ten Thanh Vien Trong",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Pattern pattern=Pattern.compile("^[a-zA-Z]*(\\s[a-zA-Z]*)*$");
                    Matcher matcher=pattern.matcher(nameMember);
                    if(matcher.matches()==false){
                        Toast.makeText(AddLoanSlip.this,"Ten Khong Hop Le",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                String maThanhVien=EDT_maThanhVien.getText().toString().trim();
                if(maThanhVien.length()==0){
                    Toast.makeText(AddLoanSlip.this, "Ma thanh vien trong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tenSachandMaSach.length()==0) {
                    Toast.makeText(AddLoanSlip.this, "Khong co sach duoc chon !", Toast.LENGTH_SHORT).show();
                    return;
                }
                String arrayMaSachandTenSach[]=tenSachandMaSach.split(":");
                String MaSach=arrayMaSachandTenSach[0].trim();
                String TenSach=arrayMaSachandTenSach[1].trim();

                int trangthai=0;
                String Currenttime=""+YearCurrent+"-"+MonthCurrent+"-"+DayCurrent;
                    if(YearSelect==0&&MonthSelect==0&&DaySelect==0) {
                        Toast.makeText(AddLoanSlip.this, "Chon thoi han", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String selectTime=""+YearSelect+"-"+MonthSelect+"-"+DaySelect;
               if(YearSelect<YearCurrent){
                   Toast.makeText(AddLoanSlip.this, "Thoi han khong hop le", Toast.LENGTH_SHORT).show();
                   return;
               }
               if(YearSelect==YearCurrent) {
                   if (MonthSelect < MonthCurrent) {
                       {
                           Toast.makeText(AddLoanSlip.this, "Thoi han khong hop le", Toast.LENGTH_SHORT).show();
                           return;
                       }
                   }
                   if (MonthSelect == MonthCurrent) {
                       if (DaySelect <= DayCurrent) {
                           {
                               Toast.makeText(AddLoanSlip.this, "Thoi han khong hop le", Toast.LENGTH_SHORT).show();
                               return;
                           }
                       }
                   }
               }

                LibraryDB newdb=new LibraryDB(AddLoanSlip.this,"DATABASEThuVien",null,1);
                LoanSlipDAO loanSlipDao =new LoanSlipDAO(newdb);
                List<LoanSlip> list= loanSlipDao.getAllPhieuMuon();
                if(list!=null)
                    for(LoanSlip x:list){
                        if(x.getMaphieu().equalsIgnoreCase(maPhieu)){
                            Toast.makeText(AddLoanSlip.this,"Mã phiếu đã tồn tại",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                LoanSlip loanSlip =new LoanSlip(maPhieu,idThuthu,TenSach,MaSach,nameMember,maThanhVien,trangthai,Currenttime,selectTime);
                loanSlipDao.insertPhieumuon(loanSlip);
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