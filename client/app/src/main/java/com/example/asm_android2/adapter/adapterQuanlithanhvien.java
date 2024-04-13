package com.example.asm_android2.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm_android2.R;
import com.example.asm_android2.dataBase.DAOMember;
import com.example.asm_android2.dataBase.DAOPhieuMuon;
import com.example.asm_android2.dataBase.DATABASEThuvien;
import com.example.asm_android2.infoManageThuThu.Member;
import com.example.asm_android2.infoManageThuThu.Phieumuon;

import java.util.List;

public class adapterQuanlithanhvien extends BaseAdapter {
    private Context context;

    private DATABASEThuvien dbThuVien;
    private DAOMember daoMember;
    private List<Member> listMember;
    public adapterQuanlithanhvien(Context context) {
        this.context = context;
        dbThuVien=new DATABASEThuvien(context,"DATABASEThuVien",null,1);
        daoMember=new DAOMember(dbThuVien);
    }

    @Override
    public int getCount() {
        listMember=daoMember.getAllMember();
        if(listMember==null) return 0;
        return listMember.size();
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
        convertView=layoutInflater.inflate(R.layout.member,null);
        Member member=listMember.get(position);

            TextView a=convertView.findViewById(R.id.TVidmember);
            a.setText("mã thành viên : "+member.getMadinhdanh());
            TextView b=convertView.findViewById(R.id.TVtenthanhvien);
            b.setText("Tên thành viên : "+member.getNameMember());
            DAOPhieuMuon daoPhieuMuon=new DAOPhieuMuon(dbThuVien);
            Log.d("123321", "getView: ");
            List<Phieumuon> listPhieu=daoPhieuMuon.getAllPhieuMuon();
            int tongsosach=0;
            int sachchuatra=0;
            if(listPhieu!=null)
             for(Phieumuon x:listPhieu){
                if(x.getDinhdanhMember().equalsIgnoreCase(member.getMadinhdanh())) {
                    tongsosach++;
                    if(x.getTrangthai()==0) sachchuatra++;
                }
            }
            TextView c=convertView.findViewById(R.id.TVsachdatra);
            c.setText("số sách đã trả : "+(tongsosach-sachchuatra));
            TextView d=convertView.findViewById(R.id.TVsosachdamuon);
            d.setText("số sách đã mượn : "+tongsosach);
        return convertView;
    }
}
