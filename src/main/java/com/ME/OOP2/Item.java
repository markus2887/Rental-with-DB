package com.ME.OOP2;

import com.ME.OOP2.entity.Car;
import com.ME.OOP2.entity.SportsCar;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/*
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(Car.class),
        @JsonSubTypes.Type(SportsCar.class)
})
*/

public abstract class Item {
    protected double price;
    protected String description;

    public Item(){
    }

    public Item(double price, String description) {
        this.price = price;
        this.description = description;
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
}
