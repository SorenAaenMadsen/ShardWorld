package com.saaenmadsen.shardworld.actors.company.culture;

import java.util.Random;

public class CompanyCulture {
    private final CompanyCulture_InnovativenessLevel innovativenessLevel;
    private final CompanyCulture_StockManagementLevel stockManagementLevel;


    public CompanyCulture() {
        super();
        Random dice = new Random();
        this.innovativenessLevel = CompanyCulture_InnovativenessLevel.getRandom(dice);
        this.stockManagementLevel = CompanyCulture_StockManagementLevel.getRandom(dice);
    }

    public CompanyCulture_InnovativenessLevel getInnovativenessLevel() {
        return innovativenessLevel;
    }

    public CompanyCulture_StockManagementLevel getStockManagementLevel() {
        return stockManagementLevel;
    }

    @Override
    public String toString() {
        return "CompanyCulture{" +
                "innovativenessLevel=" + innovativenessLevel +
                ", stockManagementLevel=" + stockManagementLevel +
                '}';
    }
}
