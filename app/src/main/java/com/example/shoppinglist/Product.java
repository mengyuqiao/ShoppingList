package com.example.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Product {
    private String name;
    private float price;
    private int num;
    private String date;
    private boolean isSelected;

    public Product() {
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
        this.num = 1;
        this.isSelected = false;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
