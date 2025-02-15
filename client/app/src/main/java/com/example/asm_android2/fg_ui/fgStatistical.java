package com.example.asm_android2.fg_ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_android2.R;
import com.example.asm_android2.dao.BookDAO;
import com.example.asm_android2.dao.LoanSlipDAO;
import com.example.asm_android2.dao.LibraryDB;
import com.example.asm_android2.modal.LoanSlip;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fgStatistical#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fgStatistical extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fgStatistical() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fgThongke.
     */
    // TODO: Rename and change types and number of parameters
    public static fgStatistical newInstance(String param1, String param2) {
        fgStatistical fragment = new fgStatistical();
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
        View view = inflater.inflate(R.layout.fragment_fg_statistical, container, false);
        EditText EDT_date_start = view.findViewById(R.id.EDT_date_start);
        EditText EDT_date_end = view.findViewById(R.id.EDT_date_end);
        Button BTN_statistical = view.findViewById(R.id.BTN_statistical);
        TextView TV_statistical=view.findViewById(R.id.TV_statistical);
        BTN_statistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String time_start = EDT_date_start.getText().toString();
                String time_end = EDT_date_end.getText().toString();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter alternateDateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
                try {
                    LocalDate.parse(time_start, dateFormatter);
                    LocalDate.parse(time_end, dateFormatter);
                } catch (DateTimeParseException e) {
                    try {
                        LocalDate.parse(time_start, alternateDateFormatter);
                        LocalDate.parse(time_end, alternateDateFormatter);
                    }
                    catch (Exception ee){
                        Toast.makeText(getContext(),"NGÀY KHÔNG HỢP LÊ !", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
                String s1[]=time_start.split("-");
                String s2[]=time_end.split("-");
                Log.d("value s1", "onClick: "+s1[0]+"-"+s1[1]+"-"+s1[2]);
                if(Integer.parseInt(s1[0])>Integer.parseInt(s2[0])) return;
                if(Integer.parseInt(s1[0])==Integer.parseInt(s2[0])){
                    if(Integer.parseInt(s1[1])>Integer.parseInt(s2[1])) return;
                    if(Integer.parseInt(s1[1])==Integer.parseInt(s2[1])){
                        if(Integer.parseInt(s1[2])>Integer.parseInt(s2[2])) return;
                    }
                }
                LibraryDB dbLibrary=new LibraryDB(getContext(),"DATABASEThuVien",null,1);
                LoanSlipDAO loanSlipDao =new LoanSlipDAO(dbLibrary);
                List<LoanSlip> list= loanSlipDao.getAllLoanSlip();
                dbLibrary.getReadableDatabase().close();
                if(list==null){
                 //   TV_statistical.setText("Doanh thu 2 đồng");
                    return;
                }
                List<LoanSlip> listSelect=new ArrayList<>();

              tt: for(int i=0;i<list.size();i++){
                   String time[]=list.get(i).getRentalDate().split("-");
                   Log.d("nam thang ngay", time[0]+"-"+time[1]+"-"+time[2]);
                   if(Integer.parseInt(time[0])>Integer.parseInt(s2[0])) continue;
                   if(Integer.parseInt(time[0])==Integer.parseInt(s2[0])){  // kiem tra nam co <= nam lua chon ->ket thuc
                        if(Integer.parseInt(time[1])>Integer.parseInt(s2[1])) continue tt;
                        if(Integer.parseInt(time[1])==Integer.parseInt(s2[1])){
                           if(Integer.parseInt(time[2])>Integer.parseInt(s2[2])) continue tt;
                        }
                   }
                  if(Integer.parseInt(time[0])<Integer.parseInt(s1[0])) continue;
                  if(Integer.parseInt(time[0])==Integer.parseInt(s1[0])){  // kiem tra nam co <= nam lua chon ->ket thuc
                      if(Integer.parseInt(time[1])<Integer.parseInt(s1[1])) continue tt;
                      if(Integer.parseInt(time[1])==Integer.parseInt(s1[1])){
                          if(Integer.parseInt(time[2])<Integer.parseInt(s1[2])) continue tt;
                      }
                  }
                  listSelect.add(list.get(i));

               }
               Toast.makeText(getContext(),"size : "+listSelect.size(),Toast.LENGTH_SHORT).show();
              int sum=0;
             //   TV_statistical.setText("Doanh thu 5 đồng");

              for(LoanSlip x:listSelect){
                  String bookCode= new BookDAO(dbLibrary).getBookCodeById(x.getIdBook());
                  SQLiteDatabase database=new LibraryDB(getContext(),"DATABASEThuVien",null,1).getReadableDatabase();
                  Cursor cursor=database.rawQuery("select * from book where bookCode= '"+bookCode+"'",null);
                  while (cursor.moveToNext()){
                      sum+=cursor.getInt(3);
                  }
              }
                TV_statistical.setText("Doanh thu "+sum+" đồng");
            }
        });
        return view;
    }
}