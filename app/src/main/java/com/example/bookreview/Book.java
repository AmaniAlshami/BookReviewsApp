package com.example.bookreview;

public class Book {

    String bookname, author , desc ;
    String image ;

    public Book() {

    }

    public Book(String bookname, String author, String desc, String image) {
        this.bookname = bookname;
        this.author = author;
        this.desc = desc;
        this.image = image;
    }

    public Book(String bookname, String image) {
        this.bookname = bookname;
        this.image = image;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
