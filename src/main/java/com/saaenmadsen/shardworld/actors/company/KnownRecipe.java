package com.saaenmadsen.shardworld.actors.company;

import com.saaenmadsen.shardworld.constants.Recipe;

import java.util.Objects;

public class KnownRecipe {
    private final Recipe recipe;
    private int profitability;
    private int expectedDailySaleValue_daily5percentChange = 10;
    private int expectedDailySaleValue_daily10percentChange = 10;
    private int expectedDailySaleValue_daily20percentChange = 10;

    public Recipe getRecipe() {
        return recipe;
    }

    public KnownRecipe(Recipe recipe, int forecastedDailySalesValue) {
        this.recipe = recipe;
        this.expectedDailySaleValue_daily5percentChange = forecastedDailySalesValue;
        this.expectedDailySaleValue_daily10percentChange = forecastedDailySalesValue;
        this.expectedDailySaleValue_daily20percentChange = forecastedDailySalesValue;
    }



    public Recipe recipe() {
        return recipe;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (KnownRecipe) obj;
        return Objects.equals(this.recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe);
    }

    @Override
    public String toString() {
        return "KnownRecipe{" +
                "recipe=" + recipe.name() +
                ", profitability=" + profitability +
                ", expectedDailySaleAmount_daily5percent=" + expectedDailySaleValue_daily5percentChange +
                ", expectedDailySaleAmount_daily10percent=" + expectedDailySaleValue_daily10percentChange +
                ", expectedDailySaleAmount_daily20percent=" + expectedDailySaleValue_daily20percentChange +
                '}';
    }

    public int getProfitability() {
        return profitability;
    }

    public void setProfitability(int profitability) {
        this.profitability = profitability;
    }


    public int getExpectedDailySaleValue_daily5percentChange() {
        return expectedDailySaleValue_daily5percentChange;
    }

    public int getExpectedDailySaleValue_daily10percentChange() {
        return expectedDailySaleValue_daily10percentChange;
    }

    public int getExpectedDailySaleValue_daily20percentChange() {
        return expectedDailySaleValue_daily20percentChange;
    }

    public void updateExpectedDailySaleValue(int soldAmountToday) {
        expectedDailySaleValue_daily5percentChange = (expectedDailySaleValue_daily5percentChange * 19 + soldAmountToday) / 20;
        expectedDailySaleValue_daily10percentChange = (expectedDailySaleValue_daily10percentChange * 9 + soldAmountToday) / 10;
        expectedDailySaleValue_daily20percentChange = (expectedDailySaleValue_daily20percentChange * 4 + soldAmountToday) / 5;
    }
}
