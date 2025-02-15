package com.example.asm_android2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.asm_android2.modal.Admin;
import com.example.asm_android2.R;
import com.example.asm_android2.modal.LibrarianAdminControl;
import java.util.List;

public class AdapterListLibrarian extends BaseAdapter {
    private List<LibrarianAdminControl> librarianList;
    private Context context;
    public AdapterListLibrarian(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        this.librarianList = Admin.getLibrarianAdminControlList();
        return librarianList.size();
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
        convertView=layoutInflater.inflate(R.layout.info_librarian,null);
        TextView TV_LibrarianName=convertView.findViewById(R.id.TV_LibrarianName);
        TextView TV_username=convertView.findViewById(R.id.TV_username);
        TextView TV_pass_word=convertView.findViewById(R.id.TV_pass_word);
        TextView TV_dinhdanh=convertView.findViewById(R.id.TV_dinhdanh);
        TV_LibrarianName.setText("Tên thủ thư :"+librarianList.get(position).getLibrarianName());
        TV_username.setText("User name :"+librarianList.get(position).getUserName());
        TV_pass_word.setText("Pass word :"+librarianList.get(position).getPassword());
        TV_dinhdanh.setText("Định danh :"+librarianList.get(position).getDinhdanh());
        return convertView;
    }
}
