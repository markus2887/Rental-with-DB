package com.ME.service;

public class LevelTwoPricePolicy implements PricePolicy {
    @Override
    public double calcPrice(double price) {
        return price * 0.75;
    }
}
