package com.example.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Product {
    private String name;
    private int num;

    public Product() {
    }

    public Product(String name) {
        this.name = name;
        this.num = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
