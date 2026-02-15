package com.ME.service;

public class NormalPricePolicy implements PricePolicy {
    @Override
    public double calcPrice(double price) {
        return price;
    }
}