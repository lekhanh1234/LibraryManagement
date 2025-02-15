package com.example.asm_android2.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_android2.ServerService.NetworkUtils;
import com.example.asm_android2.R;
import com.example.asm_android2.dao.CategoryDAO;
import com.example.asm_android2.dao.LoanSlipDAO;
import com.example.asm_android2.dao.BookDAO;
import com.example.asm_android2.dao.LibraryDB;
import com.example.asm_android2.modal.Book;

import java.util.List;

public class AdapterBook extends BaseAdapter {
    Context context;
    List<Book> bookList;
    public AdapterBook(Context context, List<Book> bookList){
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        if(bookList==null) {
            return 0;
        }
        return bookList.size();
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
        convertView=layoutInflater.inflate(R.layout.info_book,null);
            Book book=bookList.get(position);
            TextView a=convertView.findViewById(R.id.TVidsach);
            a.setText("mã sách :"+book.getBookCode());
            TextView b=convertView.findViewById(R.id.TVtensach);
            b.setText("Tên sách :"+book.getBookName());
            TextView c=convertView.findViewById(R.id.TVgiathue);
            c.setText("giá thuê :"+book.getPrice());
            TextView d=convertView.findViewById(R.id.TVloaisach);
            LibraryDB db=new LibraryDB(context,"DATABASEThuVien",null,1);
            String categoryName = new CategoryDAO(db).getNamebyId(book.getIdCategory());
            d.setText("loại sách :"+categoryName);
            ImageButton IMG_DeleteBook=convertView.findViewById(R.id.deleteBook);
            IMG_DeleteBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(NetworkUtils.isNetworkAvailable(context)==false){
                        Toast.makeText(context,"KIỂM TRA KẾT NỐI INTERNET ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    LibraryDB libraryDB =new LibraryDB(context,"DATABASEThuVien",null,1);
                    LoanSlipDAO loanSlipDao =new LoanSlipDAO(libraryDB);
                    BookDAO bookDao =new BookDAO(libraryDB);
                    int idBook= bookDao.getIdByBookCode(book.getBookCode());
                    Log.d("id ma sach can xoa", "onClick: ");
                    if(loanSlipDao.checkLoanSlipByIdBook(idBook)==true){
                        Toast.makeText(context,"THAO TAC KHONG THANH CONG ! SACH DANG NAM TRONG DANH MUC PHIEU MU0N",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        deleteBook(idBook);
                        notifyDataSetChanged();
                    }
                }
            });
        return convertView;
    }
    public void deleteBook(int idBook){
        LibraryDB dbLibrary=new LibraryDB(context,"DATABASEThuVien",null,1);
        BookDAO bookDao =new BookDAO(dbLibrary);
        bookDao.deleteBookById(idBook);
        dbLibrary.getWritableDatabase().close();
    }
}
