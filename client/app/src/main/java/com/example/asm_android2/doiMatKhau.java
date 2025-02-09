package com.example.asm_android2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm_android2.OperationSever.checkInternet;
import com.example.asm_android2.dataBase.DAOThuthu;
import com.example.asm_android2.dataBase.DATABASEThuvien;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link doiMatKhau#newInstance} factory method to
 * create an instance of this fragment.
 */
public class doiMatKhau extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public doiMatKhau() {
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
    public static doiMatKhau newInstance(String param1, String param2) {
        doiMatKhau fragment = new doiMatKhau();
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
        View view= inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        EditText EDT_password=view.findViewById(R.id.password);
        EditText EDT_newpassword=view.findViewById(R.id.EDT_matkhaumoi);
        EditText EDT_newpassword2=view.findViewById(R.id.EDT_nhaplai_matkhaumoi);
        Button BT_confirm=view.findViewById(R.id.BT_xacnhan);
        BT_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInternet.isNetworkAvailable(getContext())==false){
                    Toast.makeText(getContext(),"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }
                String password=EDT_password.getText().toString().trim();
                String newpassword=EDT_newpassword.getText().toString().trim();
                String newpassword2=EDT_newpassword2.getText().toString().trim();
                DATABASEThuvien dbThuVien=new DATABASEThuvien(getContext(),"DATABASEThuVien",null,1);
                DAOThuthu daoThuthu=new DAOThuthu(dbThuVien);

                if(password.length()==0||daoThuthu.checkPassword(password)==false){
                    Toast.makeText(getContext(),"MẬT KHẨU KHÔNG HỢP LỆ",Toast.LENGTH_LONG).show();
                    return;
                }
                if(newpassword.equals(newpassword2)==false){
                    Toast.makeText(getContext(),"MẬT KHẨU MỚI KHÔNG KHỚP",Toast.LENGTH_LONG).show();
                    return;
                }
                if(newpassword.length()<7||newpassword2.length()<7){
                    Toast.makeText(getContext(),"MẬT KHẨU MỚI PHẢI ÍT NHẤT 8 KÍ TỰ",Toast.LENGTH_LONG).show();
                    return;
                }
                // THAY DOI MAT KHAU TRONG DATABASE VA TREN SEVER;
                daoThuthu.changePassWord(newpassword,password);
                Toast.makeText(getContext(),"MAT KHAU DA DUOC THAY DOI",Toast.LENGTH_SHORT).show();
                //
            }
        });
        return view;
    }
}