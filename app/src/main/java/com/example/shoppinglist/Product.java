package com.example.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Product {
    private String name;
    private float price;
    private String date;

    public Product() {
    }

    public Product(String name, float price) {
        this.name = name;
        this.price = price;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        this.date = sdf.format(new Date());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", date='" + date + '\'' +
                '}';
    }
}
