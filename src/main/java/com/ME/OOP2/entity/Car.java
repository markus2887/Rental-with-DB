package com.ME.OOP2.entity;

import com.ME.OOP2.Item;
import com.ME.OOP2.SequenceNumberProviderLazy;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/*
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Car.class, name = "car"),
        @JsonSubTypes.Type(value = SportsCar.class, name = "sportscar")
})
 */

public class Car extends Item {

    private int id;

    protected String brand;
    protected String model;
    protected String year;
    protected String color;

    public Car(){
    }

    public Car(double price, String description, String brand, String model, String year, String color) {
        super(price, description);
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.id = SequenceNumberProviderLazy.getInstance().getNextNumber();
    }

    public int getId() {
        return id;
    }

    public void setId (int id) { this.id = id; }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
