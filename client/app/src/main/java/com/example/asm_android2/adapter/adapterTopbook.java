package com.example.asm_android2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm_android2.R;
import com.example.asm_android2.dao.BookDAO;
import com.example.asm_android2.dao.LoanSlipDAO;
import com.example.asm_android2.dao.LibraryDB;
import com.example.asm_android2.modal.LoanSlip;

import java.util.ArrayList;
import java.util.List;

public class AdapterTopbook extends BaseAdapter {
    Context context;
    List<amountBook> list=new ArrayList<>();

    public AdapterTopbook(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        LibraryDB dbLibrary=new LibraryDB(context,"DATABASEThuVien",null,1);
        LoanSlipDAO loanSlipDao =new LoanSlipDAO(dbLibrary);
        List<LoanSlip> listAllPhieu= loanSlipDao.getAllLoanSlip();
        list.clear();
        if(listAllPhieu==null) return 0;
        for(LoanSlip x:listAllPhieu){
            String bookCode= new BookDAO(dbLibrary).getBookCodeById(x.getIdBook());
            String bookName= new BookDAO(dbLibrary).getNameById(x.getIdBook());
            if(list.size()!=0) {
                for (int i=0;i<list.size();i++) {
                    if (list.get(i).bookCode.equals(bookCode)) {
                        list.get(i).value++;
                        break;
                    }
                    if(i==list.size()-1){
                        list.add(new amountBook(bookCode,bookName,1));
                        break;
                    }
                }
            }
            else{
                list.add(new amountBook(bookCode,bookName,1));
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
        dbLibrary.getReadableDatabase().close();
        dbLibrary.getWritableDatabase().close();
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
        convertView=layoutInflater.inflate(R.layout.book_top,null);
        TextView TVBookName=convertView.findViewById(R.id.TV_namebook);
        TextView TVBookCode=convertView.findViewById(R.id.TV_bookCode);
        TextView TVAmount=convertView.findViewById(R.id.TV_amount);
        TVBookName.setText(list.get(position).bookName);
            TVBookCode.setText("Mã sách : "+list.get(position).bookCode);
            TVAmount.setText("Số lượng : "+list.get(position).value);
            return convertView;
    }
    class amountBook{
        private String bookCode;
        private String bookName;
        private int value;

        public amountBook(String bookCode,String bookName, int value) {
            this.bookCode = bookCode;
            this.bookName=bookName;
            this.value = value;
        }
    }
}
