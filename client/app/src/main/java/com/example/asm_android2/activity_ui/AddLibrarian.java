package com.example.asm_android2.activity_ui;
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
import com.example.asm_android2.R;
import com.example.asm_android2.dao.LibrarianDAO;
import com.example.asm_android2.modal.Admin;
import com.example.asm_android2.ServerService.NetworkUtils;
import com.example.asm_android2.modal.LibrarianAdminControl;
import java.util.List;

public class AddLibrarian extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_thuthu);
        ActionBar a=getSupportActionBar();
        a.setTitle("Thêm Thủ Thư");
        a.setDisplayHomeAsUpEnabled(true);
        EditText EDT_LibrarianName=findViewById(R.id.EDT_LibrarianName);
        EditText EDT_LibrarianUserName=findViewById(R.id.EDT_LibrarianUserName);
        EditText EDT_passWord=findViewById(R.id.EDT_passWord);
        EditText EDT_dinhDanh=findViewById(R.id.EDT_dinhDanh);
        Button BTN_ConfirmAddLibrarian=findViewById(R.id.BTN_ConfirmAddLibrarian);
        BTN_ConfirmAddLibrarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtils.isNetworkAvailable(AddLibrarian.this)==false){
                    Toast.makeText(AddLibrarian.this,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }
                String name=EDT_LibrarianName.getText().toString().trim();
                String userName=EDT_LibrarianUserName.getText().toString().trim();
                String passWord=EDT_passWord.getText().toString().trim();
                String dinhdanh=EDT_dinhDanh.getText().toString().trim();
                if(name.length()==0){
                    Toast.makeText(AddLibrarian.this,"TÊN KHÔNG HỢP LỆ",Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i=0;i<name.length();i++){
                    if((name.charAt(i)<'A'||(name.charAt(i)>'Z'&&name.charAt(i)<'a'))&&name.codePointAt(i)!=32){
                        Toast.makeText(AddLibrarian.this,"TÊN KHÔNG HỢP LỆ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(userName.length()<5){
                    Toast.makeText(AddLibrarian.this,"USER NAME PHẢI CHỨA ÍT NHẤT 5 KÍ TỰ",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passWord.length()<5){
                    Toast.makeText(AddLibrarian.this,"PASSWORD PHẢI CHỨA ÍT NHẤT 5 KÍ TỰ",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dinhdanh.length()<3){
                    Toast.makeText(AddLibrarian.this,"DINH DANH TRỐNG PHẢI CHỨA ÍT NHẤT 3 KÍ TỰ",Toast.LENGTH_SHORT).show();
                    return;
                }

                // KIEM TRA DINH DANH VOI CAC TAI KHOAN THU THU CON LAI\
                List<LibrarianAdminControl> list= Admin.getLibrarianAdminControlList();
                for(LibrarianAdminControl x: list){
                    if(x.getDinhdanh().equalsIgnoreCase(dinhdanh)){
                        Toast.makeText(AddLibrarian.this,"MÃ ĐỊNH DANH THỦ THƯ TRÙNG LẶP",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // TIENS HÀNH CẬP NHẬT DƯ LIEU TREN SEVER
                boolean result=new LibrarianDAO(null).addLibrarianAccountToSever(name,userName,passWord,dinhdanh);
                if(result==false){
                    Toast.makeText(AddLibrarian.this,"MÃ ĐỊNH DANH THỦ THƯ ĐÃ TỒN TẠI",Toast.LENGTH_SHORT).show();
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