package com.example.asm_android2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class from_main extends AppCompatActivity {
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static int fragmene=1;

    public static int getFragmene() {
        return fragmene;
    }

    public static void setFragmene(int fragmene) {
        from_main.fragmene = fragmene;
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
                    from_main.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgQuanlisach()).commit();
                    getSupportActionBar().setTitle("Quản lí sách");
                    setFragmene(1);
                }
                if(item.getItemId()==R.id.itemNV_quanlithanhvien){
                    from_main.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgQuanlithanhvien()).commit();
                    getSupportActionBar().setTitle("Quản lí thành viên");
                    setFragmene(2);
                }
                if(item.getItemId()==R.id.itemNV_quanliloaisach){
                    from_main.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgQuanliloaisach()).commit();
                    getSupportActionBar().setTitle("Quản lí loại sách");
                    setFragmene(3);
                }
                if(item.getItemId()==R.id.itemNV_quanliphieumuon){
                    from_main.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgQuanliphieumuon()).commit();
                    getSupportActionBar().setTitle("Quản lí phiếu mượn");
                    setFragmene(4);
                }
                if(item.getItemId()==R.id.itemNV_top10sachmuon){
                    from_main.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new top10sachchomuon()).commit();
                    getSupportActionBar().setTitle("10 sách mượn nhiều nhất");
                    setFragmene(5);
                }
                if(item.getItemId()==R.id.itemNV_doanhso){
                    from_main.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgThongke()).commit();
                    getSupportActionBar().setTitle("Doanh số");
                    setFragmene(6);
                }
                if(item.getItemId()==R.id.itemNV_doimatkhau){
                    from_main.this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new doiMatKhau()).commit();
                    getSupportActionBar().setTitle("Đổi mật khẩu");
                    setFragmene(7);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fgQuanliphieumuon()).commit();
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