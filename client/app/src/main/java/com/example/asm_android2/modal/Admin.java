package com.example.asm_android2.modal;

import java.util.ArrayList;

public class Admin {
    private static int id;
    private static String userName;
    private static String password;
    private static String name;
    private static String dinhdanh;
    private static ArrayList<Librarian> list;

    public static ArrayList<Librarian> getLibrarianList() {
        return list;
    }

    public static void setLibrarianList(ArrayList<Librarian> list) {
        Admin.list = list;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Admin.id = id;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Admin.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Admin.password = password;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Admin.name = name;
    }

    public static ArrayList<Librarian> getList() {
        return list;
    }

    public static void setList(ArrayList<Librarian> list) {
        Admin.list = list;
    }

    public static String getDinhdanh() {
        return dinhdanh;
    }

    public static void setDinhdanh(String dinhdanh) {
        Admin.dinhdanh = dinhdanh;
    }
}
