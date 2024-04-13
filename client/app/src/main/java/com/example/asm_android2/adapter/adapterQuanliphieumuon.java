package com.example.asm_android2.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.asm_android2.R;
import com.example.asm_android2.dataBase.DAOLoaiSach;
import com.example.asm_android2.dataBase.DAOPhieuMuon;
import com.example.asm_android2.dataBase.DAOSach;
import com.example.asm_android2.dataBase.DATABASEThuvien;
import com.example.asm_android2.infoManageThuThu.Phieumuon;

import java.util.List;

public class adapterQuanliphieumuon extends BaseAdapter {
    private Context context;
    private DATABASEThuvien dbThuVien;
    private DAOPhieuMuon daoPhieuMuon;
    private List<Phieumuon> listPhieumuon;

    public adapterQuanliphieumuon(Context context) {
        this.context = context;
        dbThuVien=new DATABASEThuvien(context,"DATABASEThuVien",null,1);
        daoPhieuMuon=new DAOPhieuMuon(dbThuVien);
    }

    @Override
    public int getCount() {
        listPhieumuon=daoPhieuMuon.getAllPhieuMuon();
        if ((listPhieumuon==null)) {
            dbThuVien.getReadableDatabase().close();
            return 0;
        }
        dbThuVien.getReadableDatabase().close();
        return listPhieumuon.size();
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
        Phieumuon phieumuon=listPhieumuon.get(position);
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        convertView=layoutInflater.inflate(R.layout.infophieumuon,null);
        TextView TVidphieu=convertView.findViewById(R.id.TVidphieu);
        TextView TVtensach=convertView.findViewById(R.id.TVtenloaisach);
        TextView TVthanhvien=convertView.findViewById(R.id.TVthanhvien);
        TextView TVgiathue=convertView.findViewById(R.id.TVgiathue);
        TextView TVstatus=convertView.findViewById(R.id.TVstatus);
        TextView TVngaythue=convertView.findViewById(R.id.TVngaythue);
        TextView TVthoihan=convertView.findViewById(R.id.TVthoihan);
        ImageView IMG_setTrangthai=convertView.findViewById(R.id.IMG_select_trangthai);
        ImageButton IMG_deletePhieu=convertView.findViewById(R.id.IMG_deletePhieu);
        IMG_setTrangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Chọn trạng thái");

                String[] items = {"Đã trả", "Chưa trả"};

                int checkedItem = -1;

                builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DATABASEThuvien daoThuVien=new DATABASEThuvien(context,"DATABASEThuVien",null,1);
                        DAOPhieuMuon daoPhieuMuon=new DAOPhieuMuon(daoThuVien);
                        switch (which) {
                            case 0:
                                TVstatus.setText("Tính trạng : Đã trả");
                                // da tra ung voi ma trang thai la 1
                                daoPhieuMuon.thayDoiTrangThai(phieumuon.getMaphieu(),1);
                                break;
                            case 1:
                                TVstatus.setText("Tính trạng : Chưa trả");
                                // chua tra ung voi ma trang thai la 0
                                daoPhieuMuon.thayDoiTrangThai(phieumuon.getMaphieu(),0);
                                break;
                        }
                        daoThuVien.getWritableDatabase().close();
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

            TVidphieu.setText("Mã phiếu : "+phieumuon.getMaphieu());
            TVtensach.setText("Tên sách : "+phieumuon.getTensach());
            TVthanhvien.setText("Thành viên : "+phieumuon.getMember());
            DAOSach daoSach=new DAOSach(dbThuVien);
            int giathue=daoSach.getGiaTriTheoMaSach(phieumuon.getMasach());
            TVgiathue.setText("Giá thuê : "+giathue);
            String trangthai=phieumuon.getTrangthai()!=0 ? "Đã trả" : "Chưa trả";
            TVstatus.setText("Tính trạng : "+trangthai);
            TVngaythue.setText("Ngày thuê : "+phieumuon.getNgaythue());
            TVthoihan.setText("Thời hạn : "+phieumuon.getThoihan());

        IMG_deletePhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Xóa phiếu");
                alertDialogBuilder.setMessage("Hành động này là không thể khôi phục ! Vẫn tiếp tục");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        daoPhieuMuon.deleteData(listPhieumuon.get(position).getMaphieu());
                        notifyDataSetChanged();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return convertView;
    }
}
