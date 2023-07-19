package com.phili.business.entity;

public class Product {
    private String title;
    private double price;
    private int inventoryNumber;
    private int stock;
    private double vatRate;

    public Product(String title, double price, int inventoryNumber, int stock, double vatRate) {
        this.title = title;
        this.price = price;
        this.inventoryNumber = inventoryNumber;
        this.stock = stock;
        this.vatRate = vatRate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }
    public void print() {
        System.out.println(this);
    }
}
