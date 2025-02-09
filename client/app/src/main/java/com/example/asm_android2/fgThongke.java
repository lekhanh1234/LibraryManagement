package com.example.asm_android2;

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

import com.example.asm_android2.dataBase.DAOPhieuMuon;
import com.example.asm_android2.dataBase.DAOSach;
import com.example.asm_android2.dataBase.DATABASEThuvien;
import com.example.asm_android2.infoManageThuThu.Phieumuon;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fgThongke#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fgThongke extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fgThongke() {
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
    public static fgThongke newInstance(String param1, String param2) {
        fgThongke fragment = new fgThongke();
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
        View view = inflater.inflate(R.layout.fragment_fg_thongke, container, false);
        EditText EDT_time_start = view.findViewById(R.id.EDT_thoigian_start);
        EditText EDT_time_end = view.findViewById(R.id.EDT_thoigian_ketthuc);
        Button BT_thongke = view.findViewById(R.id.BTN_thongke);
        TextView TV_doanhthu=view.findViewById(R.id.TV_doanhthu);
        BT_thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String time_start = EDT_time_start.getText().toString();
                String time_end = EDT_time_end.getText().toString();
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
                TV_doanhthu.setText("Doanh thu 1 đồng");

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


                TV_doanhthu.setText("Doanh thu 10 đồng");


                DATABASEThuvien dbThuVien=new DATABASEThuvien(getContext(),"DATABASEThuVien",null,1);
                DAOPhieuMuon daoPhieuMuon=new DAOPhieuMuon(dbThuVien);
                List<Phieumuon> list=daoPhieuMuon.getAllPhieuMuon();
                dbThuVien.getReadableDatabase().close();
                if(list==null){
                    TV_doanhthu.setText("Doanh thu 2 đồng");
                    return;
                }
                List<Phieumuon> listSelect=new ArrayList<>();

              tt: for(int i=0;i<list.size();i++){
                   String time[]=list.get(i).getNgaythue().split("-");
                   Log.d("nam thang ngay", time[0]+"-"+time[1]+"-"+time[2]);
                   if(Integer.parseInt(time[0])>Integer.parseInt(s2[0])) continue;
                   if(Integer.parseInt(time[0])==Integer.parseInt(s2[0])){  // kiem tra nam co <= nam lua chon ->ket thuc
                        if(Integer.parseInt(time[1])>Integer.parseInt(s2[1])) continue tt;
                        if(Integer.parseInt(time[1])==Integer.parseInt(s2[1])){
                           if(Integer.parseInt(time[2])>Integer.parseInt(s2[2])) continue tt;
                        }
                   }
                  TV_doanhthu.setText("Doanh thu 3 đồng");
                  if(Integer.parseInt(time[0])<Integer.parseInt(s1[0])) continue;
                  if(Integer.parseInt(time[0])==Integer.parseInt(s1[0])){  // kiem tra nam co <= nam lua chon ->ket thuc
                      if(Integer.parseInt(time[1])<Integer.parseInt(s1[1])) continue tt;
                      if(Integer.parseInt(time[1])==Integer.parseInt(s1[1])){
                          if(Integer.parseInt(time[2])<Integer.parseInt(s1[2])) continue tt;
                      }
                  }
                  TV_doanhthu.setText("Doanh thu 4 đồng");
                  listSelect.add(list.get(i));

               }
               Toast.makeText(getContext(),"size : "+listSelect.size(),Toast.LENGTH_SHORT).show();
              int sum=0;
                TV_doanhthu.setText("Doanh thu 5 đồng");

              for(Phieumuon x:listSelect){
                  String masach=x.getMasach();
                  SQLiteDatabase database=new DATABASEThuvien(getContext(),"DATABASEThuVien",null,1).getReadableDatabase();
                  Cursor cursor=database.rawQuery("select * from book where masach= '"+masach+"'",null);
                  while (cursor.moveToNext()){
                      sum+=cursor.getInt(3);
                  }
              }
                TV_doanhthu.setText("Doanh thu "+sum+" đồng");


            }
        });
        return view;
    }
}