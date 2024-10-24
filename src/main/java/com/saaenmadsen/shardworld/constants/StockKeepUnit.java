package com.saaenmadsen.shardworld.constants;

public enum StockKeepUnit {
    TIMBER_RAW_KG(0, "Timber, raw, kg", "Raw material", "kg", "Raw timber, newly felled and fresh. Sold pr. Kg", 5),
    FIREWOOD_KG(1, "Firewood, kg", "Fuel", "kg", "Firewood", 8),
    TIMBER_CURED_KG(2, "Timber, cured, kg", "Raw material", "kg", "Timber, cured, ready for the sawworks or chopping into firewood. Sold pr. Kg", 8),
    WOOD_KG(3, "Wood, kg", "Raw material", "kg", "Wood, either in planks, blocks or beams. Sold pr. Kg.", 10),
    RAW_PELT(4, "Raw Pelt", "Raw material", "m2", "Raw pelts. Sold in m2", 80),
    COPPER_COIN(5, "Copper, coin", "Money", "Count", "The basic money unit. Will always be sold to the market for Money", 100),
    PAIR_OF_SHUES_WOODEN(6, "Pair of Shues, Wooden", "Shue", "Count", "Shue made entirely of wood. ", 250),
    PAIR_OF_SHUES_WOODEN_WITH_LEATHER_TOP(7, "Pair of Shues, Wooden with leather top", "Shue", "Count", "Shue of wood, leather top for comfort", 300),
    RAW_PELT_WEARABLE(8, "Raw Pelt Wearable", "Jacket", "Count", "Roughly 1 m2 of raw pelt, roughly formed to be worn as a jacket", 400),
    SILVER_COIN(9, "Silver, coin", "Money", "Count", "Money unit, worth 10 copper. Will always be sold to the market for Money", 1000),
    GOLD_COIN(10, "Gold, coin", "Money", "Count", "Money unit, worth 100 copper. Will always be sold to the market for Money", 10000);


    private int arrayId;
    private final String usageCategory;
    private final String unit;
    private final String description;
    private int initialPrice;
    private String productName;


    StockKeepUnit(int arrayId, String productName, String usageCategory, String unit, String description, int initialPrice) {
        this.arrayId = arrayId;
        this.productName = productName;
        this.usageCategory = usageCategory;
        this.unit = unit;
        this.description = description;
        this.initialPrice = initialPrice;
    }

    public static StockKeepUnit getByProductName(String productName) {
        for (StockKeepUnit sku : StockKeepUnit.values()) {
            if (sku.getProductName().equals(productName)) {
                return sku;
            }
        }
        throw new IllegalArgumentException("Unknown product name: " + productName);
    }


    public int getArrayId() {
        return arrayId;
    }

    public String getProductName() {
        return productName;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public String getUsageCategory() {
        return usageCategory;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }
}
