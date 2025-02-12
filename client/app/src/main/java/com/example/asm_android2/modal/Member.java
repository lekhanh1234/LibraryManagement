package com.example.asm_android2.modal;

public class Member {
    private int idMember;
    private String dinhdanh;
    private String memberName;
    private String librarianName;

    public Member(int idMember, String dinhdanh, String memberName, String librarianName) {
        this.idMember = idMember;
        this.dinhdanh = dinhdanh;
        this.memberName = memberName;
        this.librarianName = librarianName;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public String getDinhdanh() {
        return dinhdanh;
    }

    public void setDinhdanh(String dinhdanh) {
        this.dinhdanh = dinhdanh;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getLibrarianName() {
        return librarianName;
    }

    public void setLibrarianName(String librarianName) {
        this.librarianName = librarianName;
    }
}
