package com.example.asm_android2.modal;

public class Librarian {
    private static int id;
    private static   String dinhdanh;
    private static String userName;
    private static String password;
    private static String name;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Librarian.id = id;
    }

    public static String getDinhdanh() {
        return dinhdanh;
    }

    public static void setDinhdanh(String dinhdanh) {
        Librarian.dinhdanh = dinhdanh;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Librarian.password = password;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Librarian.userName = userName;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Librarian.name = name;
    }
}


