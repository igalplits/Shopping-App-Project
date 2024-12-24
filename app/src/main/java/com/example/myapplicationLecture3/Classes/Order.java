package com.example.myapplicationLecture3.Classes;

import java.util.List;

public class Order {

    private List<OrderItem> items;
    private double totalAmount;

    public Order() {}

    public Order(List<OrderItem> items, double totalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
