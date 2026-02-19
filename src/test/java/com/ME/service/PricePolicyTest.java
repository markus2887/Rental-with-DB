package com.ME.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PricePolicyTest {

    @Test
    void calcPriceMethod_ShouldGiveTwentyfivePercentDiscount() {
        PricePolicy policy = new LevelTwoPricePolicy();

        double result = policy.calcPrice(100.0);

        assertEquals(75.0, result);
    }
}
