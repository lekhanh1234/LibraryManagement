package com.example.asm_android2.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_android2.OperationSever.checkInternet;
import com.example.asm_android2.R;
import com.example.asm_android2.dataBase.DAOPhieuMuon;
import com.example.asm_android2.dataBase.DAOSach;
import com.example.asm_android2.dataBase.DATABASEThuvien;
import com.example.asm_android2.infoManageThuThu.Book;
import com.example.asm_android2.login_thuthu;

import java.util.List;

public class adapterBook extends BaseAdapter {

    Context context;
    List<Book> listbook;
    DATABASEThuvien db;
    DAOSach daoSach;

    public adapterBook(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        db=new DATABASEThuvien(context,"DATABASEThuVien",null,1);
        daoSach=new DAOSach(db);
        listbook=daoSach.getAllSach();
        if(listbook==null) {
            db.getReadableDatabase().close();
            return 0;
        }
        db.getReadableDatabase().close();
        return listbook.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        convertView=layoutInflater.inflate(R.layout.info_book,null);
            Book book=listbook.get(position);
            TextView a=convertView.findViewById(R.id.TVidsach);
            a.setText("mã sách :"+book.getMasach());
            TextView b=convertView.findViewById(R.id.TVtensach);
            b.setText("Tên sách :"+book.getTenSach());
            TextView c=convertView.findViewById(R.id.TVgiathue);
            c.setText("giá thuê :"+book.getGiaThue());
            TextView d=convertView.findViewById(R.id.TVloaisach);
            d.setText("loại sách :"+book.getLoaiSach());
            ImageButton IMG_deleteSach=convertView.findViewById(R.id.deleteSach);
            IMG_deleteSach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkInternet.isNetworkAvailable(context)==false){
                        Toast.makeText(context,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DATABASEThuvien databaseThuvien=new DATABASEThuvien(context,"DATABASEThuVien",null,1);
                    DAOPhieuMuon daoPhieuMuon=new DAOPhieuMuon(databaseThuvien);
                    DAOSach daoSach=new DAOSach(databaseThuvien);
                    int idBook=daoSach.getIdByMaSach(book.getMasach());
                    Log.d("id ma sach can xoa", "onClick: ");
                    if(daoPhieuMuon.checkPhieuMuonByIdBook(idBook)==true){
                        Toast.makeText(context,"THAO TAC KHONG THANH CONG ! SACH DANG NAM TRONG DANH MUC PHIEU MU0N",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        deleteSach(idBook);
                        notifyDataSetChanged();
                    }
                }
            });
        return convertView;
    }
    public void deleteSach(int idBook){
        DATABASEThuvien dbThuVien=new DATABASEThuvien(context,"DATABASEThuVien",null,1);
        DAOSach daoSach=new DAOSach(dbThuVien);
        daoSach.deleteBookById(idBook);
        dbThuVien.getWritableDatabase().close();
    }
}
