package com.ME.service;

public class Level2PricePolicy implements PricePolicy {
    @Override
    public double calcPrice(double price) {
        return price * 0.75;
    }
}
