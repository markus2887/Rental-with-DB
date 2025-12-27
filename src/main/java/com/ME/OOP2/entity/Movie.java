package com.ME.OOP2.entity;
import com.ME.OOP2.Item;
import com.ME.OOP2.SequenceNumberProviderLazy;

public class Movie extends Item {
    private int id;
    private String title;
    private String genre;
    private String year;

    public Movie() {
    }

    public Movie(double price, String description, String title, String genre, String year) {
        super(price, description);
        this.id = SequenceNumberProviderLazy.getInstance().getNextNumber();
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
