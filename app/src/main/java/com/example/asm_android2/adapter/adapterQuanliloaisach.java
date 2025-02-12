package com.example.asm_android2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_android2.ServerService.NetworkUtils;
import com.example.asm_android2.R;
import com.example.asm_android2.dao.CategoryDAO;
import com.example.asm_android2.dao.BookDAO;
import com.example.asm_android2.dao.LibraryDB;
import com.example.asm_android2.modal.Category;

import java.util.List;


public class adapterQuanliloaisach extends BaseAdapter {
    Context context;
    List<Category> listCatolotyBook;

    public adapterQuanliloaisach(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        LibraryDB db=new LibraryDB(context,"DATABASEThuVien",null,1);
        CategoryDAO categoryDao =new CategoryDAO(db);
        listCatolotyBook= categoryDao.getAllLoaiSach();
        db.getReadableDatabase().close();
        return listCatolotyBook.size();
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
        convertView=layoutInflater.inflate(R.layout.infoloaissach,null);
        Category x=listCatolotyBook.get(position);
            TextView tenloaisach=convertView.findViewById(R.id.TVtenloaisach);
            tenloaisach.setText("Loại sách :"+x.getLoaisach());

            TextView masach=convertView.findViewById(R.id.TV_maSach);
            masach.setText("Mã Sách :"+x.getMasach());

            // lay ra so luong sach cua loai sach do

            TextView TV_soluong=convertView.findViewById(R.id.TVsoluong);

            LibraryDB db=new LibraryDB(context,"DATABASEThuVien",null,1);
            CategoryDAO categoryDao =new CategoryDAO(db);
            int idCatolory= categoryDao.getIdByName(x.getLoaisach());
            int amount= categoryDao.amountCatolory(idCatolory);
            TV_soluong.setText("số lượng :"+amount);
            db.getReadableDatabase().close();

            ImageButton IMG_deleteLoaiSach=convertView.findViewById(R.id.IMG_deleteLoaisach);
            IMG_deleteLoaiSach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(NetworkUtils.isNetworkAvailable(context)==false){
                        Toast.makeText(context,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    LibraryDB libraryDB =new LibraryDB(context,"DATABASEThuVien",null,1);
                    CategoryDAO categoryDao1 =new CategoryDAO(libraryDB);
                    int idCatoloryBook= categoryDao1.getIdByName(x.getLoaisach()); // vi khi them vao co so du lieu, ma sach va ten sach k the lap lai neu moi ten sach chi tuong ung 1 id
                    BookDAO bookDao =new BookDAO(libraryDB);
                    boolean resuft= bookDao.checkCatoloryBookById(idCatoloryBook);
                    if(resuft==true){
                        Toast.makeText(context,"THAO TÁC KHÔNG THÀNH CÔNG, MÃ LOẠI SÁCH CHỈ CÓ THỂ XÓA KHI KHÔNG CÒN SÁCH THÀNH PHẦN",Toast.LENGTH_LONG).show();
                    }
                    else {
                        categoryDao1.deleteLoaiSachById(idCatoloryBook);
                        notifyDataSetChanged();
                    }
                    libraryDB.getWritableDatabase().close();
                    libraryDB.getReadableDatabase().close();

                }
            });


        return convertView;
    }
}
