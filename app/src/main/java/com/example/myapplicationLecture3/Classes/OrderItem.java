package com.example.myapplicationLecture3.Classes;

public class OrderItem {
    private String name;
    private int amount;
    private float price;

    public OrderItem() {}

    public OrderItem(String name, int amount, float price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
