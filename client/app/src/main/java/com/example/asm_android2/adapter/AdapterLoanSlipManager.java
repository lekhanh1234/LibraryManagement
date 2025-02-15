package com.example.asm_android2.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.asm_android2.R;
import com.example.asm_android2.dao.LoanSlipDAO;
import com.example.asm_android2.dao.BookDAO;
import com.example.asm_android2.dao.LibraryDB;
import com.example.asm_android2.dao.MemberDAO;
import com.example.asm_android2.modal.LoanSlip;

import java.util.List;

public class AdapterLoanSlipManager extends BaseAdapter {
    private Context context;
    private LibraryDB dbLibrary;
    private LoanSlipDAO loanSlipDao;
    private List<LoanSlip> listLoanSlip;

    public AdapterLoanSlipManager(Context context) {
        this.context = context;
        dbLibrary=new LibraryDB(context,"DATABASEThuVien",null,1);
        loanSlipDao =new LoanSlipDAO(dbLibrary);
    }

    @Override
    public int getCount() {
        listLoanSlip = loanSlipDao.getAllLoanSlip();
        if ((listLoanSlip ==null)) {
            dbLibrary.getReadableDatabase().close();
            return 0;
        }
        dbLibrary.getReadableDatabase().close();
        return listLoanSlip.size();
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
        LoanSlip loanSlip = listLoanSlip.get(position);
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        convertView=layoutInflater.inflate(R.layout.info_loanslip,null);
        TextView TV_ReceiptNumber=convertView.findViewById(R.id.TVreceiptNumber);
        TextView TV_BookName=convertView.findViewById(R.id.TVBookName);
        TextView TV_Member =convertView.findViewById(R.id.TVthanhvien);
        TextView TV_Price=convertView.findViewById(R.id.TVgiathue);
        TextView TV_Status=convertView.findViewById(R.id.TVstatus);
        TextView TV_RentalDate=convertView.findViewById(R.id.TVngaythue);
        TextView TV_Dealline =convertView.findViewById(R.id.TVthoihan);
        ImageView IMG_setTrangthai=convertView.findViewById(R.id.IMG_select_trangthai);
        ImageButton IMG_deletePhieu=convertView.findViewById(R.id.IMG_deletePhieu);
        IMG_setTrangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Chọn trạng thái");
                String[] items = {"Đã trả", "Chưa trả"};
                int checkedItem = -1;
                builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LibraryDB dbLibrary=new LibraryDB(context,"DATABASEThuVien",null,1);
                        LoanSlipDAO loanSlipDao =new LoanSlipDAO(dbLibrary);
                        switch (which) {
                            case 0:
                                TV_Status.setText("Tính trạng : Đã trả");
                                // da tra ung voi ma trang thai la 1
                                loanSlipDao.changeStates(loanSlip.getReceiptNumber(),1);
                                break;
                            case 1:
                                TV_Status.setText("Tính trạng : Chưa trả");
                                // chua tra ung voi ma trang thai la 0
                                loanSlipDao.changeStates(loanSlip.getReceiptNumber(),0);
                                break;
                        }
                        dbLibrary.getWritableDatabase().close();
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

            TV_ReceiptNumber.setText("Mã phiếu : "+ loanSlip.getReceiptNumber());
            String bookName = new BookDAO(dbLibrary).getNameById(loanSlip.getIdBook());
            TV_BookName.setText("Tên sách : "+ bookName);
            String memberName = new MemberDAO(dbLibrary).getNameById(loanSlip.getIdMember());
            TV_Member.setText("Thành viên : "+ memberName);
            BookDAO bookDao =new BookDAO(dbLibrary);
            String bookCode = bookDao.getBookCodeById(loanSlip.getIdBook());
            int price= bookDao.getPriceByBookCode(bookCode);
            TV_Price.setText("Giá thuê : "+price);
            String trangthai= loanSlip.getStates()!=0 ? "Đã trả" : "Chưa trả";
            TV_Status.setText("Tính trạng : "+trangthai);
            TV_RentalDate.setText("Ngày thuê : "+ loanSlip.getRentalDate());
            TV_Dealline.setText("Thời hạn : "+ loanSlip.getDeadline());

        IMG_deletePhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Xóa phiếu");
                alertDialogBuilder.setMessage("Hành động này là không thể khôi phục ! Vẫn tiếp tục");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loanSlipDao.deleteLoanSlip(listLoanSlip.get(position).getReceiptNumber());
                        notifyDataSetChanged();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return convertView;
    }
}
