package com.saaenmadsen.shardworld.constants;

/**
 * This should be JSON files, which can then be loaded into the world settings.
 */
public enum StockKeepUnit {
    TIMBER_RAW_KG (0, "Timber, raw, kg", "Raw material", "kg", "Raw timber, newly felled and fresh. Sold pr. Kg", 5),
    FIREWOOD_KG (1, "Firewood, kg", "Fuel", "kg", "Firewood", 8),
    TIMBER_CURED_KG (2, "Timber, cured, kg", "Raw material", "kg", "Timber, cured, ready for the sawworks or chopping into firewood. Sold pr. Kg", 8),
    WOOD_KG (3, "Wood, kg", "Raw material", "kg", "Wood, either in planks, blocks or beams. Sold pr. Kg.", 10),
    RAW_PELT (4, "Raw Pelt", "Raw material", "m2", "Raw pelts. Sold in m2", 80),
    BRASS_BAR (5, "Brass, bar", "Raw material", "", "", 33),
    PAIR_OF_SHUES_WOODEN (6, "Pair of Shues, Wooden", "Shue", "Count", "Shue made entirely of wood. ", 250),
    PAIR_OF_SHUES_WOODEN_WITH_LEATHER_TOP (7, "Pair of Shues, Wooden with leather top", "Shue", "Count", "Shue of wood, leather top for comfort", 300),
    RAW_PELT_WEARABLE (8, "Raw Pelt Wearable", "Jacket", "Count", "Roughly 1 m2 of raw pelt, roughly formed to be worn as a jacket", 400),
    BRASS_PLATE (9, "Brass, plate", "Raw material", "", "", 33),
    BRASS_WIRE (10, "Brass, wire", "Raw material", "", "", 33),
    APPLES (11, "Apples", "Food", "", "", 33),
    BRONZE_TOOLS (12, "Bronze tools", "Tools", "kg", "", 33),
    CHARCOAL (13, "Charcoal", "Fuel", "", "", 33),
    COAL_MINED (14, "Coal, Mined", "Fuel", "", "", 33),
    COPPER (15, "Copper", "Raw material", "", "", 33),
    CHICKEN (16, "Chicken", "Animal", "", "", 33),
    COPPER_ORE (17, "Copper Ore", "Ore", "", "", 33),
    COPPER_TOOLS (18, "Copper tools", "Tools", "kg", "", 33),
    COPPER_COIN (19, "Copper, coin", "Money", "Count", "The basic money unit. Will always be sold to the market for Money", 33),
    GOLD_ORE (20, "Gold Ore", "Ore", "", "", 33),
    GOLD_BAR (21, "Gold, bar", "Raw material", "", "", 33),
    CORN (22, "Corn", "Food", "", "", 33),
    COW (23, "Cow", "Animal", "", "", 33),
    COWS_MILK (24, "Cows Milk", "Food", "", "", 33),
    GOLD_COIN (25, "Gold, coin", "Money", "Count", "Money unit, worth 100 copper. Will always be sold to the market for Money", 33),
    HARD_STEEL (26, "Hard Steel", "", "kg", "This technique involves melting wrought iron with carbon in small crucibles, creating steel with unique properties that allowes for strong, flexible, and sharp-edged weapons. ", 33),
    IRON_ORE (27, "Iron Ore", "Ore", "kg", "", 33),
    IRON_TOOLS (28, "Iron tools", "Tools", "kg", "", 33),
    LAPIS_LAZULI (29, "Lapis Lazuli", "Precious Stone", "", "", 33),
    MILD_STEEL (30, "Mild Steel", "Raw material", "", "Mild steel is a type of carbon steel with a low amount of carbon. It is also known as low carbon steel. ", 33),
    NULL (31, "Null", "Null", "", "Null", 0),
    MEAT_CHICKEN (32, "Meat, Chicken", "Food", "", "", 33),
    MEET_BEEF (33, "Meet, Beef", "Food", "", "", 33),
    MEET_PORK (34, "Meet, Pork", "Food", "", "", 33),
    PEAS (35, "Peas", "Food", "", "", 33),
    PIG (36, "Pig", "Animal", "", "", 33),
    POTATOES (37, "Potatoes", "Food", "", "", 33),
    QUARTZ (38, "Quartz", "Precious Stone", "", "", 33),
    RICE (39, "Rice", "Food", "", "", 33),
    RUBY (40, "Ruby", "Precious Stone", "", "", 33),
    SAPPHIRE (41, "Sapphire", "Precious Stone", "", "", 33),
    SCRAP_BRONZE_METAL (42, "Scrap Bronze metal", "", "kg", "", 33),
    SCRAP_COPPER_METAL (43, "Scrap copper metal", "", "kg", "", 33),
    SOYA (44, "Soya", "Food", "", "", 33),
    SCRAP_IRON_METAL (45, "Scrap iron metal", "", "kg", "", 33),
    SCRAP_STEEL_METAL (46, "Scrap Steel metal", "", "kg", "", 33),
    SILVER_ORE (47, "Silver Ore", "Ore", "", "", 33),
    SILVER_BAR (48, "Silver, bar", "Raw material", "", "", 33),
    SILVER_COIN (49, "Silver, coin", "Money", "Count", "Money unit, worth 10 copper. Will always be sold to the market for Money", 1000),
    STAINLESS_STEEL (50, "Stainless Steel", "", "kg", "Stainless Steel is alloyed using chromium.", 33),
    WHEAT (51, "Wheat", "Food", "", "", 33),
    WROUGHT_IRON (52, "Wrought Iron", "Raw material", "kg", "Wrought Iron is relatively soft but malleable. It contains traces of slag, making it less pure than modern iron but still highly functional for making a variety of tools and weapons.", 33),
    STEEL_TOOLS (53, "Steel tools", "Tools", "kg", "", 33),
    STONE_TOOLS (54, "Stone tools", "Tools", "kg", "", 33),
    TIN (55, "Tin", "Raw material", "", "", 33),
    TIN_ORE (56, "Tin Ore", "Ore", "", "", 33),
    TOPAZ (57, "Topaz", "Precious Stone", "", "", 33),
    WOOD_TOOLS (58, "Wood tools", "Tools", "kg", "", 33),
    ;



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
