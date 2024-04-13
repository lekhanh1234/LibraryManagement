package com.example.asm_android2;

public class Product {
    private int resImg;
    private  String name;
    private  int price;
    private  int amount;

    public Product(int resImg, String name, int price, int amount) {
        this.resImg = resImg;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public int getResImg() {
        return resImg;
    }

    public void setResImg(int resImg) {
        this.resImg = resImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
