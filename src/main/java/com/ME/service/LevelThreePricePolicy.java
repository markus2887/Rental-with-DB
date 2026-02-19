package com.ME.service;

public class LevelThreePricePolicy implements PricePolicy {
    @Override
    public double calcPrice(double price) {
        return price * 0.6;
    }
}