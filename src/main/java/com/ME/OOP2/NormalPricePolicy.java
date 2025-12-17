package com.ME.OOP2;

public class NormalPricePolicy implements PricePolicy {
    @Override
    public double calcPrice(double price) {
        return price;
    }
}