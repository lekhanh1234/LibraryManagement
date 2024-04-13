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

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.asm_android2.OperationSever.checkInternet;
import com.example.asm_android2.R;
import com.example.asm_android2.dataBase.DAOLoaiSach;
import com.example.asm_android2.dataBase.DAOSach;
import com.example.asm_android2.dataBase.DATABASEThuvien;
import com.example.asm_android2.infoManageThuThu.Book;
import com.example.asm_android2.infoManageThuThu.Loaisach;
import com.example.asm_android2.login_thuthu;

import java.util.List;


public class adapterQuanliloaisach extends BaseAdapter {
    Context context;
    List<Loaisach> listCatolotyBook;

    public adapterQuanliloaisach(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        DATABASEThuvien db=new DATABASEThuvien(context,"DATABASEThuVien",null,1);
        DAOLoaiSach daoLoaiSach=new DAOLoaiSach(db);
        listCatolotyBook=daoLoaiSach.getAllLoaiSach();
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
        Loaisach x=listCatolotyBook.get(position);
            TextView tenloaisach=convertView.findViewById(R.id.TVtenloaisach);
            tenloaisach.setText("Loại sách :"+x.getLoaisach());

            TextView masach=convertView.findViewById(R.id.TV_maSach);
            masach.setText("Mã Sách :"+x.getMasach());

            // lay ra so luong sach cua loai sach do

            TextView TV_soluong=convertView.findViewById(R.id.TVsoluong);

            DATABASEThuvien db=new DATABASEThuvien(context,"DATABASEThuVien",null,1);
            DAOLoaiSach daoLoaiSach=new DAOLoaiSach(db);
            int idCatolory=daoLoaiSach.getIdByName(x.getLoaisach());
            int amount=daoLoaiSach.amountCatolory(idCatolory);
            TV_soluong.setText("số lượng :"+amount);
            db.getReadableDatabase().close();

            ImageButton IMG_deleteLoaiSach=convertView.findViewById(R.id.IMG_deleteLoaisach);
            IMG_deleteLoaiSach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkInternet.isNetworkAvailable(context)==false){
                        Toast.makeText(context,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DATABASEThuvien databaseThuvien=new DATABASEThuvien(context,"DATABASEThuVien",null,1);
                    DAOLoaiSach daoLoaiSach1=new DAOLoaiSach(databaseThuvien);
                    int idCatoloryBook=daoLoaiSach1.getIdByName(x.getLoaisach()); // vi khi them vao co so du lieu, ma sach va ten sach k the lap lai neu moi ten sach chi tuong ung 1 id
                    DAOSach daoSach=new DAOSach(databaseThuvien);
                    boolean resuft=daoSach.checkCatoloryBookById(idCatoloryBook);
                    if(resuft==true){
                        Toast.makeText(context,"THAO TÁC KHÔNG THÀNH CÔNG, MÃ LOẠI SÁCH CHỈ CÓ THỂ XÓA KHI KHÔNG CÒN SÁCH THÀNH PHẦN",Toast.LENGTH_LONG).show();
                    }
                    else {
                        daoLoaiSach1.deleteLoaiSachById(idCatoloryBook);
                        notifyDataSetChanged();
                    }
                    databaseThuvien.getWritableDatabase().close();
                    databaseThuvien.getReadableDatabase().close();

                }
            });


        return convertView;
    }
}
