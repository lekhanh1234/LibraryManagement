package com.example.asm_android2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm_android2.dataBase.DAOPhieuMuon;
import com.example.asm_android2.dataBase.DAOSach;
import com.example.asm_android2.dataBase.DATABASEThuvien;
import com.example.asm_android2.infoManageThuThu.Phieumuon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adapterTopbook extends BaseAdapter {
    Context context;
    List<amountBook> list=new ArrayList<>();

    public adapterTopbook(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        DATABASEThuvien dbThuVien=new DATABASEThuvien(context,"DATABASEThuVien",null,1);
        DAOPhieuMuon daoPhieuMuon=new DAOPhieuMuon(dbThuVien);
        List<Phieumuon> listAllPhieu=daoPhieuMuon.getAllPhieuMuon();
        list.clear();
        if(listAllPhieu==null) return 0;
        for(Phieumuon x:listAllPhieu){
            String maSach=x.getMasach();
            String tenSach=x.getTensach();
            if(list.size()!=0) {
                for (int i=0;i<list.size();i++) {
                    if (list.get(i).maSach.equals(maSach)) {
                        list.get(i).value++;
                        break;
                    }
                    if(i==list.size()-1){
                        list.add(new amountBook(maSach,tenSach,1));
                        break;
                    }
                }
            }
            else{
                list.add(new amountBook(maSach,tenSach,1));
            }
        }
        for(int i=0;i<list.size();i++){
            for(int j=i+i;j<list.size();j++){
               if(list.get(i).value>list.get(j).value){
                   amountBook a=list.get(i);
                   list.set(i,list.get(j));
                   list.set(j,a);
               }
            }
        }
        dbThuVien.getReadableDatabase().close();
        dbThuVien.getWritableDatabase().close();
        if(list.size()>10) return 10;
        else return list.size();
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
        convertView=layoutInflater.inflate(R.layout.topsachmuon,null);
        TextView TVnamebook=convertView.findViewById(R.id.TV_namebook);
        TextView TVmaSach=convertView.findViewById(R.id.TV_maSach);
        TextView TVsoluong=convertView.findViewById(R.id.TV_amount);
            TVnamebook.setText(list.get(position).tensach);
            TVmaSach.setText("Mã sách : "+list.get(position).maSach);
            TVsoluong.setText("Số lượng : "+list.get(position).value);
        return convertView;
    }
    class amountBook{
        private String maSach;
        private String tensach;
        private int value;

        public amountBook(String maSach,String tensach, int value) {
            this.maSach = maSach;
            this.tensach=tensach;
            this.value = value;
        }
    }
}
