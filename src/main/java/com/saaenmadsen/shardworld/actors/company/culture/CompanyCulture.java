package com.saaenmadsen.shardworld.actors.company.culture;

import java.util.Random;

public class CompanyCulture {
    private final CompanyCulture_InnovativenessLevel innovativenessLevel;
    private final CompanyCulture_StockManagementLevel stockManagementLevel;


    public CompanyCulture(CompanyCulture_InnovativenessLevel innovativenessLevel, CompanyCulture_StockManagementLevel stockManagementLevel) {
        this.innovativenessLevel = innovativenessLevel;
        this.stockManagementLevel = stockManagementLevel;
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
