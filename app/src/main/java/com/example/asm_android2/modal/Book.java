package com.example.asm_android2.modal;

public class Book {
    private  Integer id;
    private String bookCode;
    private String bookName;
    private int price;
    private int idCategory;
    private int idLibrarian;
    public Book(String bookCode, String bookName, int price,int  idCategory, int idLibrarian) {
        this.bookCode = bookCode;
        this.bookName = bookName;
        this.price = price;
        this.idCategory = idCategory;
        this.idLibrarian = idLibrarian;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdLibrarian() {
        return idLibrarian;
    }

    public void setIdLibrarian(int idLibrarian) {
        this.idLibrarian = idLibrarian;
    }
}