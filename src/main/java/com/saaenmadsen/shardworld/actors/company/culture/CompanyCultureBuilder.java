package com.saaenmadsen.shardworld.actors.company.culture;

import java.util.Random;

public class CompanyCultureBuilder {
    private CompanyCulture_InnovativenessLevel innovativenessLevel;
    private CompanyCulture_StockManagementLevel stockManagementLevel;


    public CompanyCultureBuilder() {
        Random dice = new Random();
        this.innovativenessLevel = CompanyCulture_InnovativenessLevel.getRandom(dice);;
        this.stockManagementLevel = CompanyCulture_StockManagementLevel.getRandom(dice);;
    }

    private CompanyCultureBuilder(CompanyCulture_InnovativenessLevel innovativenessLevel, CompanyCulture_StockManagementLevel stockManagementLevel) {
        this.innovativenessLevel = innovativenessLevel;
        this.stockManagementLevel = stockManagementLevel;
    }

    public static CompanyCultureBuilder ofRandom(){
        return new CompanyCultureBuilder();
    }

    public static CompanyCultureBuilder of(CompanyCulture_InnovativenessLevel innovativenessLevel, CompanyCulture_StockManagementLevel stockManagementLevel){
        return new CompanyCultureBuilder(innovativenessLevel, stockManagementLevel);
    }

    public CompanyCulture_InnovativenessLevel getInnovativenessLevel() {
        return innovativenessLevel;
    }

    public CompanyCulture_StockManagementLevel getStockManagementLevel() {
        return stockManagementLevel;
    }

    public CompanyCultureBuilder withInnovativeness(CompanyCulture_InnovativenessLevel innovativeness){
        this.innovativenessLevel = innovativeness;
        return this;
    }

    public CompanyCultureBuilder withStockManagement(CompanyCulture_StockManagementLevel stockManagement){
        this.stockManagementLevel = stockManagement;
        return this;
    }

    public CompanyCulture build(){
        return new CompanyCulture(innovativenessLevel, stockManagementLevel);
    }
}
