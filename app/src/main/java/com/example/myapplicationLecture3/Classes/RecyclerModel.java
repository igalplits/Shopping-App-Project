package com.example.myapplicationLecture3.Classes;

import android.widget.ImageView;

public class RecyclerModel {
    private int date;
    private String title;
    private String description;
    private int amount;

    public RecyclerModel(int date, String title, String description, int amount) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.amount = amount;
    }

    public int getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPriceAsFloat() {
        try {
            String cleanedPrice = description.replaceAll("[^\\d.]", "");
            return Float.parseFloat(cleanedPrice);  // Convert the String price to float
        } catch (NumberFormatException e) {
            // Handle the exception, return a default value (e.g., 0) or log it
            System.out.println("Invalid price format: " + description);
            return 0.0f;  // Default value in case of error
        }
    }
}
