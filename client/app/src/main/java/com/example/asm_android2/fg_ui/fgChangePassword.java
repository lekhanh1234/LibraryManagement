package com.example.asm_android2.fg_ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_android2.R;
import com.example.asm_android2.ServerService.NetworkUtils;
import com.example.asm_android2.dao.LibrarianDAO;
import com.example.asm_android2.dao.LibraryDB;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fgChangePassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fgChangePassword extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fgChangePassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment doiMatKhau.
     */
    // TODO: Rename and change types and number of parameters
    public static fgChangePassword newInstance(String param1, String param2) {
        fgChangePassword fragment = new fgChangePassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_change_password, container, false);
        EditText EDT_password=view.findViewById(R.id.password);
        EditText EDT_newPassword=view.findViewById(R.id.EDT_newPassword);
        EditText EDT_reNewPassword=view.findViewById(R.id.EDT_reNewPassword);
        Button BTN_confirm=view.findViewById(R.id.BTN_confirm);
        BTN_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtils.isNetworkAvailable(getContext())==false){
                    Toast.makeText(getContext(),"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }
                String password=EDT_password.getText().toString().trim();
                String newpassword=EDT_newPassword.getText().toString().trim();
                String reNewPassword=EDT_reNewPassword.getText().toString().trim();
                LibraryDB dbLibrary=new LibraryDB(getContext(),"DATABASEThuVien",null,1);
                LibrarianDAO librarianDao =new LibrarianDAO(dbLibrary);

                if(password.length()==0|| librarianDao.checkPassword(password)==false){
                    Toast.makeText(getContext(),"MẬT KHẨU KHÔNG HỢP LỆ",Toast.LENGTH_LONG).show();
                    return;
                }
                if(newpassword.equals(reNewPassword)==false){
                    Toast.makeText(getContext(),"MẬT KHẨU MỚI KHÔNG KHỚP",Toast.LENGTH_LONG).show();
                    return;
                }
                if(newpassword.length()<7||reNewPassword.length()<7){
                    Toast.makeText(getContext(),"MẬT KHẨU MỚI PHẢI ÍT NHẤT 8 KÍ TỰ",Toast.LENGTH_LONG).show();
                    return;
                }
                librarianDao.changePassWord(newpassword,password);
                Toast.makeText(getContext(),"MAT KHAU DA DUOC THAY DOI",Toast.LENGTH_SHORT).show();
                //
            }
        });
        return view;
    }
}