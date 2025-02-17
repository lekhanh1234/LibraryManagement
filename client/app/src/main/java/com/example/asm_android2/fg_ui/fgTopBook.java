package com.example.asm_android2.fg_ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asm_android2.R;
import com.example.asm_android2.adapter.AdapterTopbook;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fgTopBook#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fgTopBook extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fgTopBook() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment top10sachchomuon.
     */
    // TODO: Rename and change types and number of parameters
    public static fgTopBook newInstance(String param1, String param2) {
        fgTopBook fragment = new fgTopBook();
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
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_top_book, container, false);
        ListView LV_topBook=view.findViewById(R.id.LV_topBook);
        AdapterTopbook adapterTopbook=new AdapterTopbook(getContext());
        LV_topBook.setAdapter(adapterTopbook);
        return view;
    }
}