package com.example.asm_android2.Account;

import java.util.List;

import com.example.asm_android2.dataBase.DAOLoaiSach;
import com.example.asm_android2.dataBase.DAOMember;
import com.example.asm_android2.dataBase.DAOSach;
import com.example.asm_android2.dataBase.DAOPhieuMuon;
import com.example.asm_android2.dataBase.DATABASEThuvien;
import com.example.asm_android2.infoManageThuThu.Book;
import com.example.asm_android2.infoManageThuThu.Loaisach;
import com.example.asm_android2.infoManageThuThu.Member;
import com.example.asm_android2.infoManageThuThu.Phieumuon;

public class AccountThuthu {
    private  int id; // tuong tu cmnd, chi co mot
    private String name_user;
    private  String password_user;
    private String nameThuthu;
    private String madinhdanh;

    public AccountThuthu(int id, String name_user, String password_user, String nameThuthu, String madinhdanh) {
        this.id = id;
        this.name_user = name_user;
        this.password_user = password_user;
        this.nameThuthu = nameThuthu;
        this.madinhdanh = madinhdanh;
    }

    public int getId() {
        return id;
    }

    public String getMadinhdanh() {
        return madinhdanh;
    }

    public String getName_user() {
        return name_user;
    }

    public String getPassword_user() {
        return password_user;
    }

    public String getNameThuthu() {
        return nameThuthu;
    }



}
