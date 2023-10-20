package com.jnu.student.data;

import java.io.Serializable;

public class BookItem implements Serializable {
    private String book_title;
    private int book_price;
    private final int book_image;

    public String getTitle() {
        return book_title;
    }

    public int getPrice() {
        return book_price;
    }

    public void setTitle(String title){
        book_title = title;
    }

    public void setPrice(int price){
        book_price = price;
    }

    public int getCoverResourceId() {
        return book_image;
    }

    public BookItem(String name, int price, int image) {
        book_title = name;
        book_price = price;
        book_image = image;
    }
}
