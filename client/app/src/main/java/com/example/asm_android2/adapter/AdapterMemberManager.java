package com.example.asm_android2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm_android2.R;
import com.example.asm_android2.dao.MemberDAO;
import com.example.asm_android2.dao.LoanSlipDAO;
import com.example.asm_android2.dao.LibraryDB;
import com.example.asm_android2.modal.Member;
import com.example.asm_android2.modal.LoanSlip;

import java.util.List;

public class AdapterMemberManager extends BaseAdapter {
    private Context context;

    private LibraryDB dbLibrary;
    private MemberDAO memberDAO;
    private List<Member> listMember;
    public AdapterMemberManager(Context context) {
        this.context = context;
        dbLibrary=new LibraryDB(context,"DATABASEThuVien",null,1);
        memberDAO =new MemberDAO(dbLibrary);
    }

    @Override
    public int getCount() {
        listMember= memberDAO.getAllMember();
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
        convertView=layoutInflater.inflate(R.layout.info_member,null);
        Member member=listMember.get(position);
            TextView a=convertView.findViewById(R.id.TVidmember);
            a.setText("mã thành viên : "+member.getDinhdanh());
            TextView b=convertView.findViewById(R.id.TVtenthanhvien);
            b.setText("Tên thành viên : "+member.getMemberName());
            LoanSlipDAO loanSlipDao =new LoanSlipDAO(dbLibrary);
            List<LoanSlip> listPhieu= loanSlipDao.getAllLoanSlip();
            int bookAmount=0;
            int booksNotReturned=0;
            if(listPhieu!=null)
             for(LoanSlip x:listPhieu){
                if(new MemberDAO(dbLibrary).getDinhdanhById(x.getIdMember()).equalsIgnoreCase(member.getDinhdanh())) {
                    bookAmount++;
                    if(x.getStates()==0) booksNotReturned++;
                }
            }
            TextView c=convertView.findViewById(R.id.TVsachdatra);
            c.setText("số sách đã trả : "+(bookAmount-booksNotReturned));
            TextView d=convertView.findViewById(R.id.TVsosachdamuon);
            d.setText("số sách đã mượn : "+bookAmount);
        return convertView;
    }
}
