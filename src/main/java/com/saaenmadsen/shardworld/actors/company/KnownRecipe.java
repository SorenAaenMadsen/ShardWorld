package com.saaenmadsen.shardworld.actors.company;

import com.saaenmadsen.shardworld.constants.Recipe;

import java.util.Objects;

public final class KnownRecipe {
    private final Recipe recipe;
    private final int lastProducedDay;

    public int getProfitability() {
        return profitability;
    }

    public int getLastProducedDay() {
        return lastProducedDay;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    private int profitability;

    public KnownRecipe(Recipe recipe, int lastProducedDay) {
        this.recipe = recipe;
        this.lastProducedDay = lastProducedDay;
    }

    public KnownRecipe(Recipe recipe) {
        this(recipe, 0);
    }

    public Recipe recipe() {
        return recipe;
    }

    public int lastProducedDay() {
        return lastProducedDay;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (KnownRecipe) obj;
        return Objects.equals(this.recipe, that.recipe) &&
                this.lastProducedDay == that.lastProducedDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe, lastProducedDay);
    }

    @Override
    public String toString() {
        return "KnownRecipe[" +
                "recipe=" + recipe + ", " +
                "lastProducedDay=" + lastProducedDay + ']';
    }

    public void setProfitability(int profitability) {
        this.profitability = profitability;
    }
}
