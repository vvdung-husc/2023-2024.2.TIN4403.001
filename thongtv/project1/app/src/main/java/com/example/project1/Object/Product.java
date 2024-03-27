package com.example.project1.Object;

public class Product {
    private int ResourseId;
    private String title;
    private String price;

    public Product(int resourseId, String title, String price) {
        ResourseId = resourseId;
        this.title = title;
        this.price = price;
    }

    public int getResourseId() {
        return ResourseId;
    }

    public void setResourseId(int resourseId) {
        ResourseId = resourseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
