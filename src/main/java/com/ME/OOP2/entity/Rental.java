package com.ME.OOP2.entity;

import java.time.LocalDateTime;

public class Rental {

    private static int objectCount = 1;
    private int id;
    private String name;
    private int car;
    private String startTime;
    private String endTime;
    private double price;
    private double totalPrice;
    private int level;
    private int daysToRent;

    public Rental(){
    }

    public Rental(String name, int car, String startTime, String endTime, double price, double totalPrice, int level, int daysToRent) {

        this.id = objectCount;
        this.name = name;
        this.car = car;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.totalPrice = totalPrice;
        this.level = level;
        this.daysToRent = daysToRent;
        objectCount++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjectCount() {
        return objectCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalprice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDaysToRent(int daysToRent) {
        this.daysToRent = daysToRent;
    }

    public int getDaysToRent() {
        return daysToRent;
    }


}