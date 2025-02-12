package com.example.asm_android2.modal;

public class Category {
    private  int id;
    private String categoryCode;
    private String categoryName;
    private int idLibrarian;

    public Category(int id, String categoryCode, String categoryName, int idLibrarian) {
        this.id = id;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.idLibrarian = idLibrarian;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIdLibrarian() {
        return idLibrarian;
    }

    public void setIdLibrarian(int idLibrarian) {
        this.idLibrarian = idLibrarian;
    }
}
