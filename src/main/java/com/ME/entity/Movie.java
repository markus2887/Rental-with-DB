package com.ME.entity;
import com.ME.service.RentalType;
import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", length = 3, nullable = false)
    private double price;

    @Column(name = "description", length = 3000)
    private String description;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "genre", length = 30, nullable = false)
    private String genre;

    @Column(name = "RelYear", length = 4, nullable = false)
    private String relYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "rentalType", nullable = false, length = 5)
    private RentalType rentalType = RentalType.MOVIE;

    protected Movie() {
    }

    public Movie(double price, String description, String title, String genre, String relYear) {
        this.price = price;
        this.description = description;
        this.title = title;
        this.genre = genre;
        this.relYear = relYear;
    }

    public Long getId() {
        return id;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRelYear() {
        return relYear;
    }

    public void setRelYear(String relYear) {
        this.relYear = relYear;
    }

    public RentalType getRentalType() {
        return rentalType;
    }
}
