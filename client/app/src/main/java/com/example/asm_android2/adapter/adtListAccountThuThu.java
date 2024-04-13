package com.example.asm_android2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm_android2.Account.AccountAdminLogin;
import com.example.asm_android2.Account.AccountThuthu;
import com.example.asm_android2.R;

import java.util.List;

public class adtListAccountThuThu extends BaseAdapter {
    private List<AccountThuthu> listAccountThuThu;
    private Context context;

    public adtListAccountThuThu(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        this.listAccountThuThu = AccountAdminLogin.getListAccountThuThu();
        return listAccountThuThu.size();
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
        convertView=layoutInflater.inflate(R.layout.info_account_thuthu,null);
        TextView TV_namethuthu=convertView.findViewById(R.id.TV_namethuthu);
        TextView TV_username=convertView.findViewById(R.id.TV_username);
        TextView TV_pass_word=convertView.findViewById(R.id.TV_pass_word);
        TextView TV_dinhdanh=convertView.findViewById(R.id.TV_dinhdanh);
        TV_namethuthu.setText("Tên thủ thư :"+listAccountThuThu.get(position).getNameThuthu());
        TV_username.setText("User name :"+listAccountThuThu.get(position).getName_user());
        TV_pass_word.setText("Pass word :"+listAccountThuThu.get(position).getPassword_user());
        TV_dinhdanh.setText("Định danh :"+listAccountThuThu.get(position).getMadinhdanh());
        return convertView;
    }
}
