package com.saaenmadsen.shardworld.unittest.market;

import com.saaenmadsen.shardworld.actors.countrymarket.MarketDay;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MarketDayTest {
    @Test
    public void testPriceAdjustmentsNothingSold() {
        assertThat("Nothing was sold",
                MarketDay.calculateNewPrice(1000, 1000, 1000),
                equalTo(1000));
    }

    @Test
    public void testPriceAdjustments() {
        int amountForSale = 100;
        for(int amountUnsold=0;amountUnsold<=100;amountUnsold++) {
            //for (int amountForSale = 0; amountForSale < 100; amountForSale++) {
            int percentUnsoldBucketsOfTen = (10 * amountUnsold) / (amountForSale);
                System.out.println ("amountUnsold" +amountUnsold+ " gives: " + percentUnsoldBucketsOfTen);
            //}
        }

    }
}
