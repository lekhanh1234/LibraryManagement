package com.example.asm_android2.activity_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.asm_android2.R;
import com.example.asm_android2.fg_ui.fgBookCategoryManager;
import com.example.asm_android2.fg_ui.fgBookManager;
import com.example.asm_android2.fg_ui.fgChangePassword;
import com.example.asm_android2.fg_ui.fgLoanSlipManager;
import com.example.asm_android2.fg_ui.fgMemberManager;
import com.example.asm_android2.fg_ui.fgStatistical;
import com.example.asm_android2.fg_ui.fgTopBook;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static int fragmene=1;

    public static int getFragmene() {
        return fragmene;
    }

    public static void setFragmene(int fragmene) {
        Home.fragmene = fragmene;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_main);
        Intent intent=getIntent();

        DrawerLayout drawerLayout = findViewById(R.id.drawrlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản lí Phiếu mượn");
        NavigationView navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.itemNV_quanlisach){
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgBookManager()).commit();
                    getSupportActionBar().setTitle("Quản lí sách");
                    setFragmene(1);
                }
                if(item.getItemId()==R.id.itemNV_quanlithanhvien){
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgMemberManager()).commit();
                    getSupportActionBar().setTitle("Quản lí thành viên");
                    setFragmene(2);
                }
                if(item.getItemId()==R.id.itemNV_quanliloaisach){
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgBookCategoryManager()).commit();
                    getSupportActionBar().setTitle("Quản lí loại sách");
                    setFragmene(3);
                }
                if(item.getItemId()==R.id.itemNV_quanliphieumuon){
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgLoanSlipManager()).commit();
                    getSupportActionBar().setTitle("Quản lí phiếu mượn");
                    setFragmene(4);
                }
                if(item.getItemId()==R.id.itemNV_top10sachmuon){
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgTopBook()).commit();
                    getSupportActionBar().setTitle("10 sách mượn nhiều nhất");
                    setFragmene(5);
                }
                if(item.getItemId()==R.id.itemNV_doanhso){
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgStatistical()).commit();
                    getSupportActionBar().setTitle("Doanh số");
                    setFragmene(6);
                }
                if(item.getItemId()==R.id.itemNV_doimatkhau){
                    Home.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgChangePassword()).commit();
                    getSupportActionBar().setTitle("Đổi mật khẩu");
                    setFragmene(7);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgLoanSlipManager()).commit();
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

      //  db.execSQL("create table productOfAccount(id INTEGER PRIMARY KEY autoincrement ,
    //  nameProduct nvarchar(50) not null ,price int not null,amount int not null)");


    @Override
    public void finish() {
        super.finish();
    }
}