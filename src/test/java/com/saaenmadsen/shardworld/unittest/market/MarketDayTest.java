package com.saaenmadsen.shardworld.unittest.market;

import com.saaenmadsen.shardworld.actors.countrymarket.MarketDay;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MarketDayTest {
    @Test
    public void testPriceAdjustmentsNothingSold() {
        assertThat("Lots for sale, nothing was sold",
                MarketDay.calculateNewPrice(1000, 1000, 1000, 0),
                equalTo(800));
    }

    @Test
    public void testPriceAdjustmentsDemandsMeetExactly() {
        assertThat("Lots for sale, and all is sold. No customers unfulfilled. B-E-A-utiful market day",
                MarketDay.calculateNewPrice(1000, 1000, 0, 0),
                equalTo(1000));
    }

    @Test
    public void testOneUnsold() {
        assertThat("To be testOneUnsold",
                MarketDay.calculateNewPrice(1000, 1000, 1, 0),
                equalTo(1000));
    }

    @Test
    public void testOneUnfulfilled() {
        assertThat("To be testOneUnfulfilled",
                MarketDay.calculateNewPrice(1000, 1000, 0, 1),
                equalTo(1070));
    }

    @Test
    public void testOneSoleMostLeftUnsold() {
        assertThat("To be testOneSoleMostLeftUnsold",
                MarketDay.calculateNewPrice(1000, 1000, 999, 0),
                equalTo(820));
    }

    @Test
    public void testRequiredDoubleWhatWasAvailable() {
        assertThat("To be testRequiredDoubleWhatWasAvailable",
                MarketDay.calculateNewPrice(1000, 1000, 0, 1000),
                equalTo(1100));
    }

    @Test
    public void testRequiredTenTimesWhatWasAvailable() {
        assertThat("To be testRequiredTenTimesWhatWasAvailable",
                MarketDay.calculateNewPrice(1000, 1000, 0, 9000),
                equalTo(1260));
    }

    @Test
    public void testWhenNoneIsDemandedOrOnSale(){
        assertThat("To be described",
                MarketDay.calculateNewPrice(1000, 0, 0, 0),
                equalTo(1000));
    }

    @Test
    public void testPriceContinuesToFallUntilWorthless(){
        assertThat("Price must be able to fall to worthless",
                MarketDay.calculateNewPrice(2, 1000, 1000, 0),
                equalTo(1));
        assertThat("Price must be able to fall to worthless",
                MarketDay.calculateNewPrice(1, 1000, 1000, 0),
                equalTo(0));
        assertThat("Price cannot be negative",
                MarketDay.calculateNewPrice(0, 1000, 1000, 0),
                equalTo(0));
    }

}
