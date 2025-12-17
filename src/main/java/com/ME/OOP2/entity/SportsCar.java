package com.ME.OOP2.entity;

import com.ME.OOP2.SequenceNumberProviderLazy;

public class SportsCar extends Car {
    private int id;
    private boolean sportSeats;
    private int hp;

    public SportsCar() {
    }

    public SportsCar(double price, String description, String brand, String model, String year, String color, boolean sportSeats, int hp) {
        super(price, description, brand, model, year, color);
        this.id = SequenceNumberProviderLazy.getInstance().getNextNumber();
        this.sportSeats = sportSeats;
        this.hp = hp;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public boolean getSportSeats() {
        return sportSeats;
    }

    public void setSportSeats(boolean sportSeats) {
        this.sportSeats = sportSeats;
    }

    public int getHp() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }


}
