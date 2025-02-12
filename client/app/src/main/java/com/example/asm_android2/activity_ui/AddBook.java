package com.example.asm_android2.activity_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_android2.R;
import com.example.asm_android2.modal.Librarian;
import com.example.asm_android2.ServerService.NetworkUtils;
import com.example.asm_android2.dao.CategoryDAO;
import com.example.asm_android2.dao.BookDAO;
import com.example.asm_android2.dao.LibraryDB;
import com.example.asm_android2.modal.Book;
import com.example.asm_android2.modal.Category;

import java.util.List;

public class AddBook extends AppCompatActivity {
    String nameCatoloryBook="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thêm sách");
        EditText EDT_maSach=findViewById(R.id.EDT_maSach);
        EditText EDT_nameBook=findViewById(R.id.EDT_nameBook);
        EditText EDT_giaThue=findViewById(R.id.EDT_giaThue);
        Spinner spinner=(Spinner)findViewById(R.id.spinner);

        CategoryDAO categoryDao =new CategoryDAO(new LibraryDB(this,"DATABASEThuVien",null,1));
        List<Category> listLoaiSach= categoryDao.getAllLoaiSach();
        String listNameCatoloryBook[]=null;
        if(listLoaiSach.size()!=0){
            listNameCatoloryBook=new String[listLoaiSach.size()];
            for(int i=0;i<listLoaiSach.size();i++){
            listNameCatoloryBook[i]=listLoaiSach.get(i).getLoaisach();
        }
        }
        else listNameCatoloryBook=new String[]{""};
        ArrayAdapter<String> x=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listNameCatoloryBook) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextSize(20); // Đặt kích thước chữ
                tv.setBackgroundColor(getResources().getColor(R.color.grey));
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextSize(20); // Đặt kích thước chữ
                return view;
            }
        };
        spinner.setAdapter(x);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameCatoloryBook=((TextView)view).getText().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button BTN_addBook=findViewById(R.id.BTN_confirmAddBook);
        BTN_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtils.isNetworkAvailable(AddBook.this)==false){
                    Toast.makeText(AddBook.this,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }
                String masach=EDT_maSach.getText().toString().trim();
                String tensach=EDT_nameBook.getText().toString().trim();
                String tenloaisach=nameCatoloryBook;

                if(masach.length()==0||tensach.length()==0){
                    Toast.makeText(AddBook.this,"Dữ Liệu Trống",Toast.LENGTH_SHORT).show();
                    return;
                }
                int giathue=0;
                try {
                    giathue=Integer.parseInt(EDT_giaThue.getText().toString().trim());
                    if(giathue<=0) Toast.makeText(AddBook.this,"Giá thuê sách không hợp lệ !",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(AddBook.this,"Giá thuê sách không hợp lệ !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tenloaisach.length()==0){
                    Toast.makeText(AddBook.this,"Không có thông tin loại sách !",Toast.LENGTH_SHORT).show();
                    return;
                }

                LibraryDB libraryDB =new LibraryDB(AddBook.this,"DATABASEThuVien",null,1);
                boolean ketqua= new BookDAO(libraryDB).insertBook(new Book(masach,tensach,giathue,tenloaisach, Librarian.getId()));
                libraryDB.getWritableDatabase().close();
                Log.d("hamfinish duoc goi", "onClick: ");
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