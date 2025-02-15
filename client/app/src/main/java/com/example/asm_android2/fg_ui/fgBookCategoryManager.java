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
import com.example.asm_android2.adapter.AdapterBookManager;
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
        View view = inflater.inflate(R.layout.fragment_fg_category_manager, container, false);
        ListView listView=view.findViewById(R.id.LV_categoryManager);
        AdapterBookManager AdapterBookManager =new AdapterBookManager(getContext());
        listView.setAdapter(AdapterBookManager);
        Button BTN_addCategoryBook=view.findViewById(R.id.BTN_addCategoryBook);
        ConstraintLayout CL_addCategory=view.findViewById(R.id.CL_addCategory);
        CL_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CL_addCategory.setVisibility(View.GONE);
            }
        });
        BTN_addCategoryBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CL_addCategory.setVisibility(View.VISIBLE);
            }
        });
        Button BTN_confirm=view.findViewById(R.id.BTN_confirmAddCatoloryBook);
        EditText EDT_categoryName=view.findViewById(R.id.EDT_categoryName);
        EditText EDT_bookCode=view.findViewById(R.id.EDT_bookCode);
        BTN_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtils.isNetworkAvailable(getContext())==false){
                    Toast.makeText(getContext(),"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                    return;
                }
                String categoryName=EDT_categoryName.getText().toString().trim();
                String bookCode=EDT_bookCode.getText().toString().trim();
                if(categoryName.length()==0) return;
                if(bookCode.length()==0) return;
                LibraryDB dbLibrary=new LibraryDB(getContext(),"DATABASEThuVien",null,1);
                CategoryDAO categoryDao =new CategoryDAO(dbLibrary);
                boolean resuft= categoryDao.insertCategory(new Category(-1,bookCode,categoryName, Librarian.getId()));
                if(resuft==false){
                    Toast.makeText(getContext(),"Thao tác không thành công !",Toast.LENGTH_SHORT).show();
                    dbLibrary.getWritableDatabase().close();
                    return;
                }
                dbLibrary.getWritableDatabase().close();
                AdapterBookManager.notifyDataSetChanged();
                CL_addCategory.setVisibility(View.GONE);
            }
        });
        return view;
    }
}

