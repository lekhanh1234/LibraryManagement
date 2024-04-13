package com.example.asm_android2.infoManageThuThu;

import android.widget.TextView;

import com.example.asm_android2.R;

public class Member {
    private int idMember;
    private String madinhdanh;
    private String nameMember;
    private String nameThuthu;

    public Member(int idMember, String madinhdanh, String nameMember, String nameThuthu) {
        this.idMember = idMember;
        this.madinhdanh = madinhdanh;
        this.nameMember = nameMember;
        this.nameThuthu = nameThuthu;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public String getMadinhdanh() {
        return madinhdanh;
    }

    public void setMadinhdanh(String madinhdanh) {
        this.madinhdanh = madinhdanh;
    }

    public String getNameMember() {
        return nameMember;
    }

    public void setNameMember(String nameMember) {
        this.nameMember = nameMember;
    }

    public String getNameThuthu() {
        return nameThuthu;
    }

    public void setNameThuthu(String nameThuthu) {
        this.nameThuthu = nameThuthu;
    }
}
