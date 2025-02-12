package com.example.asm_android2.fg_ui;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asm_android2.R;
import com.example.asm_android2.modal.Librarian;
import com.example.asm_android2.ServerService.NetworkUtils;
import com.example.asm_android2.adapter.adapterQuanliloaisach;
import com.example.asm_android2.dao.CategoryDAO;
import com.example.asm_android2.dao.LibraryDB;
import com.example.asm_android2.modal.Category;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fgBookCategoryManager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fgBookCategoryManager extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fgBookCategoryManager() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fgQuanliloaisach.
     */
    // TODO: Rename and change types and number of parameters
    public static fgBookCategoryManager newInstance(String param1, String param2) {
        fgBookCategoryManager fragment = new fgBookCategoryManager();
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
        View view = inflater.inflate(R.layout.fragment_fg_quanliloaisach, container, false);
        ListView listView=view.findViewById(R.id.LV_quanliloaisach);
        adapterQuanliloaisach adapterQuanliloaisach=new adapterQuanliloaisach(getContext());
        listView.setAdapter(adapterQuanliloaisach);
        Button BTN_addBook=view.findViewById(R.id.BTN_addCatoloryBook);
        ConstraintLayout CL_addBook=view.findViewById(R.id.CL_addBook);
        CL_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CL_addBook.setVisibility(View.GONE);
            }
        });
        BTN_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CL_addBook.setVisibility(View.VISIBLE);
            }
        });

        Button BTN_confirm=view.findViewById(R.id.BTN_confirmAddCatoloryBook);
        EditText EDT_nameCatoloryBook=view.findViewById(R.id.EDT_addCatoloryBook);
        EditText EDT_maSach=view.findViewById(R.id.EDT_maSach);
        BTN_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtils.isNetworkAvailable(getContext())==false){
                    Toast.makeText(getContext(),"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }
                String nameCatoloryBook=EDT_nameCatoloryBook.getText().toString().trim();
                String masach=EDT_maSach.getText().toString().trim();
                if(nameCatoloryBook.length()==0) return;
                if(masach.length()==0) return;
                LibraryDB dbThuVien=new LibraryDB(getContext(),"DATABASEThuVien",null,1);
                CategoryDAO categoryDao =new CategoryDAO(dbThuVien);

                boolean resuft= categoryDao.insertCategory(new Category(masach,nameCatoloryBook, Librarian.getId()));
                if(resuft==false){
                    Toast.makeText(getContext(),"Thao tác không thành công !",Toast.LENGTH_SHORT).show();
                    dbThuVien.getWritableDatabase().close();
                    return;
                }
                dbThuVien.getWritableDatabase().close();
                adapterQuanliloaisach.notifyDataSetChanged();
                CL_addBook.setVisibility(View.GONE);
            }
        });

        return view;
    }
}

