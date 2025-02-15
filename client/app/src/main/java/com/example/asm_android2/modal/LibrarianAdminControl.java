package com.example.asm_android2.modal;

public class LibrarianAdminControl {
    private int id;
    private String userName;
    private String password;
    private String librarianName;
    private String dinhdanh;

    public LibrarianAdminControl(int id,String userName, String password, String librarianName, String dinhdanh) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.librarianName = librarianName;
        this.dinhdanh = dinhdanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLibrarianName() {
        return librarianName;
    }

    public void setLibrarianName(String librarianName) {
        this.librarianName = librarianName;
    }

    public String getDinhdanh() {
        return dinhdanh;
    }

    public void setDinhdanh(String dinhdanh) {
        this.dinhdanh = dinhdanh;
    }
}
