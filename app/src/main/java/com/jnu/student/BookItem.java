package com.jnu.student;

public class BookItem {
    private final String book_name;
    private final String book_price;
    private final int book_image;
    public String getTitle() {
        return book_name;
    }
    public String getPrice() {
        return book_price;
    }
    public int getCoverResourceId() {
        return book_image;
    }
    BookItem(String name, String price, int image){
        book_name = name;
        book_price = price;
        book_image = image;
    }
}
