package com.saaenmadsen.shardworld.actors.company.culture;

import java.util.Random;

public enum CompanyCulture_StockManagementLevel {
    CRITICAL_SHORTAGE(1, "Severe stock shortage, high risk of supply chain disruption; extreme JIT approach with no safety stock"),
    MINIMAL_STOCK(2, "Maintains minimal stock levels to reduce holding costs, relies heavily on JIT with low safety margin"),
    LEAN_JIT(3, "Operates on a lean JIT model with minimal inventory, reordering as needed to meet demand"),
    BASIC_JIT(4, "Follows basic JIT principles with some stock on hand to buffer minor demand fluctuations"),
    STABLE_REPLENISHMENT(5, "Maintains stable replenishment cycles to balance JIT with a moderate inventory buffer"),
    MODERATE_STOCK_BUILD(6, "Building moderate stock levels to prevent stockouts, blending JIT with periodic restocking"),
    STRATEGIC_STOCK(7, "Holds strategic stock levels to secure against supply chain risks, balancing JIT with prepared reserves"),
    HIGH_STOCK_BUFFER(8, "Maintains a high stock buffer to ensure availability, with regular inventory reviews and JIT elements"),
    FULL_WAREHOUSING(9, "Operates with substantial inventory, prepared for high demand surges; minimal JIT influence"),
    EXCESSIVE_STOCKPILE(10, "Significantly overstocked, prioritizing complete stock availability over cost; no JIT reliance");

    private final int level;
    private final String explanation;

    CompanyCulture_StockManagementLevel(int level, String explanation) {
        this.level = level;
        this.explanation = explanation;
    }

    public static CompanyCulture_StockManagementLevel getRandom(Random dice) {
        return values()[dice.nextInt(values().length)];
    }


    public int getLevel() {
        return level;
    }

    public String getExplanation() {
        return explanation;
    }
}