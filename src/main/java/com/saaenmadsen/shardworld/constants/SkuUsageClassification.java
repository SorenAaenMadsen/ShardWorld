package com.saaenmadsen.shardworld.constants;

public enum SkuUsageClassification {
    RAW_MATERIAL("Raw material"),
    FUEL("Fuel"),
    APPAREL("Apparel"),
    FOOD("Food"),
    TOOLS("Tools"),
    ANIMAL("Animal"),
    ORE("Ore"),
    JEWELRY("Jewelry"),
    OTHER("Other");
    private final String printableName;

    SkuUsageClassification(String printableName) {
        this.printableName = printableName;
    }

    public String getPrintableName() {
        return printableName;
    }
}
