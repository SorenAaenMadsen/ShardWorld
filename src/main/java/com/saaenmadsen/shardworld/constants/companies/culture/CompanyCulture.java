package com.saaenmadsen.shardworld.constants.companies.culture;

import java.util.Random;

public class CompanyCulture {
    private final CompanyCulture_InnovativenessLevel innovativenessLevel;


    public CompanyCulture() {
        super();
        Random dice = new Random();
        this.innovativenessLevel = CompanyCulture_InnovativenessLevel.getRandom(dice);
    }

    public CompanyCulture_InnovativenessLevel getInnovativenessLevel() {
        return innovativenessLevel;
    }

    @Override
    public String toString() {
        return "CompanyCulture{" +
                "innovativenessLevel=" + innovativenessLevel +
                '}';
    }
}
