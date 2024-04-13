package com.example.asm_android2.Account;

import java.util.ArrayList;

public class AccountAdminLogin {
    private static int id;
    private static String userName;
    private static String passWord;
    private static String nameAdmin;
    private static String dinhdanh;
    private static ArrayList<AccountThuthu> list;

    public static ArrayList<AccountThuthu> getListAccountThuThu() {
        return list;
    }

    public static void setListAccountThuThu(ArrayList<AccountThuthu> list) {
        AccountAdminLogin.list = list;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        AccountAdminLogin.id = id;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        AccountAdminLogin.userName = userName;
    }

    public static String getPassWord() {
        return passWord;
    }

    public static void setPassWord(String passWord) {
        AccountAdminLogin.passWord = passWord;
    }

    public static String getNameAdmin() {
        return nameAdmin;
    }

    public static void setNameAdmin(String nameAdmin) {
        AccountAdminLogin.nameAdmin = nameAdmin;
    }

    public static String getDinhdanh() {
        return dinhdanh;
    }

    public static void setDinhdanh(String dinhdanh) {
        AccountAdminLogin.dinhdanh = dinhdanh;
    }
}
