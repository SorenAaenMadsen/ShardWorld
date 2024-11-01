package com.saaenmadsen.shardworld.actors.company.culture;

import java.util.Random;

public enum CompanyCulture_InnovativenessLevel {
    NOT_INNOVATIVE(1, "Not Innovative"),
    LOW_INNOVATIVE(2, "Low Innovative"),
    MODERATELY_INNOVATIVE(3, "Moderately Innovative"),
    SOMEWHAT_INNOVATIVE(4, "Somewhat Innovative"),
    INNOVATIVE(5, "Innovative"),
    QUITE_INNOVATIVE(6, "Quite Innovative"),
    HIGHLY_INNOVATIVE(7, "Highly Innovative"),
    VERY_HIGHLY_INNOVATIVE(8, "Very Highly Innovative"),
    EXCEPTIONALLY_INNOVATIVE(9, "Exceptionally Innovative"),
    RADICALLY_INNOVATIVE(10, "Radically Innovative");

    private final int level;
    private final String description;

    CompanyCulture_InnovativenessLevel(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public static CompanyCulture_InnovativenessLevel getRandom(Random dice) {
        return values()[dice.nextInt(values().length)];
    }

    @Override
    public String toString() {
        return "CompanyCulture_InnovativenessLevel{" +
                "level=" + level +
                ", description='" + description + '\'' +
                '}';
    }
}