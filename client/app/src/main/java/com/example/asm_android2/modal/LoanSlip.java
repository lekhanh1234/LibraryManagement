package com.example.asm_android2.modal;

public class LoanSlip {
    private int id;
    private String receiptNumber;
    private int idLibrarian;
    private int idBook;
    private int idMember;
    private int states;
    private String rentalDate;
    private String deadline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public int getIdLibrarian() {
        return idLibrarian;
    }

    public void setIdLibrarian(int idLibrarian) {
        this.idLibrarian = idLibrarian;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public LoanSlip(int id, String receiptNumber, int idLibrarian, int idBook, int idMember, int states, String rentalDate, String deadline) {
        this.id = id;
        this.receiptNumber = receiptNumber;
        this.idLibrarian = idLibrarian;
        this.idBook = idBook;
        this.idMember = idMember;
        this.states = states;
        this.rentalDate = rentalDate;
        this.deadline = deadline;
    }
}