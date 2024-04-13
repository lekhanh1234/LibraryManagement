package com.example.asm_android2.infoManageThuThu;

public class Book {
    private String masach;
    private String tenSach;
    private int giaThue;
    private String LoaiSach;
    private int idthuthu;
    public Book(String masach, String tenSach, int giaThue, String loaiSach, int idthuthu) {
        this.masach = masach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        LoaiSach = loaiSach;
        this.idthuthu = idthuthu;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public String getLoaiSach() {
        return LoaiSach;
    }

    public void setLoaiSach(String loaiSach) {
        LoaiSach = loaiSach;
    }
}