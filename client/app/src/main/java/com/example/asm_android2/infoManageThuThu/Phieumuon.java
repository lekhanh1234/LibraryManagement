package com.example.asm_android2.infoManageThuThu;

public class Phieumuon {
    private String maphieu;
    private int idthuthu;
    private String tensach;
    private String masach;
    private String member;
    private String dinhdanhMember;
    private int trangthai;
    private String ngaythue;
    private String thoihan;

    public Phieumuon(String maphieu, int idthuthu, String tensach, String masach, String member, String dinhdanhMember, int trangthai, String ngaythue, String thoihan) {
        this.maphieu = maphieu;
        this.idthuthu = idthuthu;
        this.tensach = tensach;
        this.masach = masach;
        this.member = member;
        this.dinhdanhMember = dinhdanhMember;
        this.trangthai = trangthai;
        this.ngaythue = ngaythue;
        this.thoihan = thoihan;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getDinhdanhMember() {
        return dinhdanhMember;
    }

    public void setDinhdanhMember(String dinhdanhMember) {
        this.dinhdanhMember = dinhdanhMember;
    }

    public String getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(String maphieu) {
        this.maphieu = maphieu;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getNgaythue() {
        return ngaythue;
    }

    public void setNgaythue(String ngaythue) {
        this.ngaythue = ngaythue;
    }

    public String getThoihan() {
        return thoihan;
    }

    public void setThoihan(String thoihan) {
        this.thoihan = thoihan;
    }
}