package com.ME.entity;

import com.ME.service.RentalType;
import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "releaseYear")
    private String releaseYear;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "rented", nullable = false)
    private boolean rented;

    @Enumerated(EnumType.STRING)
    @Column(name = "rentalType", nullable = false, length = 5)
    private RentalType rentalType = RentalType.CAR;

    protected Car(){
    }

    public Car(double price, String description, String brand, String model, String releaseYear, String color) {
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.model = model;
        this.releaseYear = releaseYear;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

}
