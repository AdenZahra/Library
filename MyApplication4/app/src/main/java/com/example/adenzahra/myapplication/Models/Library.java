package com.example.adenzahra.myapplication.Models;

/**
 * Created by Aden Zahra on 1/26/2018.
 */

public class Library {
    private String name;
    private String isbn;
    private String author;
    private String cost;
    private int id;

    public Library(int id, String name, String isbn, String author,String cost) {
        setId(id);
        setName(name);
        setIsbn(isbn);
        setAuthor(author);
        setCost(cost);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {

        this.name = name;
    }

    public String getIsbn()
    {

        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

