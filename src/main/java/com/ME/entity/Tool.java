package com.ME.entity;

import com.ME.service.RentalType;
import jakarta.persistence.*;

@Entity
@Table(name = "tools")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "fromYear", length = 4)
    private String fromYear;

    @Column(name = "cordless", length = 3)
    private String cordless;

    @Column(name = "rented", nullable = false)
    private boolean rented;

    @Enumerated(EnumType.STRING)
    @Column(name = "rentalType", nullable = false, length = 5)
    private RentalType rentalType = RentalType.TOOL;

    protected Tool() {
    }

    public Tool(double price, String description, String name, String fromYear, String cordless) {
        this.price = price;
        this.description = description;
        this.name = name;
        this.fromYear = fromYear;
        this.cordless = cordless;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromYear() {
        return fromYear;
    }

    public void setFromYear(String fromYear) {
        this.fromYear = fromYear;
    }

    public String getCordless() {
        return cordless;
    }

    public void setCordless(String cordless) {
        this.cordless = cordless;
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