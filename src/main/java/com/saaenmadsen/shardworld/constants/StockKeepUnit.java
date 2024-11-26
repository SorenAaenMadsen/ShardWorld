package com.saaenmadsen.shardworld.constants;

/**
 * This should be JSON files, which can then be loaded into the world settings.
 */
public enum StockKeepUnit {
    TIMBER_RAW_KG (0, "Timber, raw, kg", SkuUsageClassification.RAW_MATERIAL, "kg", "Raw timber, newly felled and fresh. Sold pr. Kg", 3),
    FIREWOOD_KG (1, "Firewood, kg", SkuUsageClassification.FUEL, "kg", "Firewood", 3),
    TIMBER_CURED_KG (2, "Timber, cured, kg", SkuUsageClassification.RAW_MATERIAL, "kg", "Timber, cured, ready for the sawworks or chopping into firewood. Sold pr. Kg", 205),
    WOOD_KG (3, "Wood, kg", SkuUsageClassification.RAW_MATERIAL, "kg", "Wood, either in planks, blocks or beams. Sold pr. Kg.", 22),
    RAW_PELT (4, "Raw Pelt", SkuUsageClassification.RAW_MATERIAL, "m2", "Raw pelts. Sold in m2", 80),
    BRASS_BAR (5, "Brass, bar", SkuUsageClassification.INACTIVEMETAL, "", "", 33),
    PAIR_OF_SHUES_WOODEN (6, "Pair of Shues, Wooden", SkuUsageClassification.APPAREL, "Count", "Shue made entirely of wood. ", 312),
    PAIR_OF_SHUES_WOODEN_WITH_LEATHER_TOP (7, "Pair of Shues, Wooden with leather top", SkuUsageClassification.APPAREL, "Count", "Shue of wood, leather top for comfort", 300),
    RAW_PELT_WEARABLE (8, "Raw Pelt Wearable", SkuUsageClassification.APPAREL, "Count", "Roughly 1 m2 of raw pelt, roughly formed to be worn as a jacket", 400),
    BRASS_PLATE (9, "Brass, plate", SkuUsageClassification.INACTIVEMETAL, "", "", 33),
    BRASS_WIRE (10, "Brass, wire", SkuUsageClassification.INACTIVEMETAL, "", "", 33),
    APPLES (11, "Apples", SkuUsageClassification.FOOD, "", "", 33),
    BRONZE_TOOLS (12, "Bronze tools", SkuUsageClassification.INACTIVETOOLS, "kg", "", 33),
    CHARCOAL (13, "Charcoal", SkuUsageClassification.FUEL, "", "", 22),
    COAL_MINED (14, "Coal, Mined", SkuUsageClassification.FUEL, "", "", 176),
    COPPER (15, "Copper", SkuUsageClassification.METAL, "", "", 6019),
    CHICKEN (16, "Chicken", SkuUsageClassification.ANIMAL, "", "", 33),
    COPPER_ORE (17, "Copper Ore", SkuUsageClassification.ORE, "", "", 198),
    COPPER_TOOLS (18, "Copper tools", SkuUsageClassification.TOOLS, "kg", "", 7173),
    COPPER_COIN (19, "Copper, coin", SkuUsageClassification.OTHER, "Count", "The basic money unit. Will always be sold to the market for Money", 33),
    GOLD_ORE (20, "Gold Ore", SkuUsageClassification.ORE, "", "", 33),
    GOLD_BAR (21, "Gold, bar", SkuUsageClassification.INACTIVEMETAL, "", "", 33),
    CORN (22, "Corn", SkuUsageClassification.FOOD, "", "", 33),
    COW (23, "Cow", SkuUsageClassification.ANIMAL, "", "", 33),
    COWS_MILK (24, "Cows Milk", SkuUsageClassification.FOOD, "", "", 33),
    GOLD_COIN (25, "Gold, coin", SkuUsageClassification.OTHER, "Count", "Money unit, worth 100 copper. Will always be sold to the market for Money", 33),
    HARD_STEEL (26, "Hard Steel", SkuUsageClassification.METAL, "kg", "This technique involves melting wrought iron with carbon in small crucibles, creating steel with unique properties that allowes for strong, flexible, and sharp-edged weapons. ", 2318),
    IRON_ORE (27, "Iron Ore", SkuUsageClassification.ORE, "kg", "", 132),
    IRON_TOOLS (28, "Iron tools", SkuUsageClassification.TOOLS, "kg", "", 2318),
    LAPIS_LAZULI (29, "Lapis Lazuli", SkuUsageClassification.OTHER, "", "", 2318),
    MILD_STEEL (30, "Mild Steel", SkuUsageClassification.METAL, "", "Mild steel is a type of carbon steel with a low amount of carbon. It is also known as low carbon steel. ", 2318),
    WOOD_TOOLS (31, "Wood tools", SkuUsageClassification.TOOLS, "kg", "", 378),
    MEAT_CHICKEN (32, "Meat, Chicken", SkuUsageClassification.FOOD, "", "", 206),
    MEET_BEEF (33, "Meet, Beef", SkuUsageClassification.FOOD, "", "", 206),
    MEET_PORK (34, "Meet, Pork", SkuUsageClassification.FOOD, "", "", 206),
    PEAS (35, "Peas", SkuUsageClassification.FOOD, "", "", 330),
    PIG (36, "Pig", SkuUsageClassification.ANIMAL, "", "", 33),
    POTATOES (37, "Potatoes", SkuUsageClassification.FOOD, "", "", 330),
    QUARTZ (38, "Quartz", SkuUsageClassification.OTHER, "", "", 33),
    RICE (39, "Rice", SkuUsageClassification.FOOD, "", "", 33),
    RUBY (40, "Ruby", SkuUsageClassification.OTHER, "", "", 33),
    SAPPHIRE (41, "Sapphire", SkuUsageClassification.OTHER, "", "", 33),
    SCRAP_BRONZE_METAL (42, "Scrap Bronze metal", SkuUsageClassification.INACTIVEMETAL, "kg", "", 33),
    SCRAP_COPPER_METAL (43, "Scrap copper metal", SkuUsageClassification.INACTIVEMETAL, "kg", "", 33),
    SOYA (44, "Soya", SkuUsageClassification.FOOD, "", "", 33),
    SCRAP_IRON_METAL (45, "Scrap iron metal", SkuUsageClassification.INACTIVEMETAL, "kg", "", 33),
    SCRAP_STEEL_METAL (46, "Scrap Steel metal", SkuUsageClassification.INACTIVEMETAL, "kg", "", 33),
    SILVER_ORE (47, "Silver Ore", SkuUsageClassification.ORE, "", "", 88),
    SILVER_BAR (48, "Silver, bar", SkuUsageClassification.INACTIVEMETAL, "", "", 33),
    SILVER_COIN (49, "Silver, coin", SkuUsageClassification.OTHER, "Count", "Money unit, worth 10 copper. Will always be sold to the market for Money", 1000),
    STAINLESS_STEEL (50, "Stainless Steel", SkuUsageClassification.INACTIVEMETAL, "kg", "Stainless Steel is alloyed using chromium.", 33),
    WHEAT (51, "Wheat", SkuUsageClassification.FOOD, "", "", 33),
    WROUGHT_IRON (52, "Wrought Iron", SkuUsageClassification.METAL, "kg", "Wrought Iron is relatively soft but malleable. It contains traces of slag, making it less pure than modern iron but still highly functional for making a variety of tools and weapons.", 1743),
    STEEL_TOOLS (53, "Steel tools", SkuUsageClassification.INACTIVETOOLS, "kg", "", 33),
    STONE_TOOLS (54, "Stone tools", SkuUsageClassification.TOOLS, "kg", "", 880),
    TIN (55, "Tin", SkuUsageClassification.INACTIVEMETAL, "", "", 33),
    TIN_ORE (56, "Tin Ore", SkuUsageClassification.ORE, "", "", 264),
    TOPAZ (57, "Topaz", SkuUsageClassification.OTHER, "", "", 33),
    ONIONS (58, "Onions", SkuUsageClassification.FOOD, "kg", "", 330),
    BERRIES (59, "Berries", SkuUsageClassification.FOOD, "kg", "", 330)
    ;



    private int arrayId;
    private final SkuUsageClassification usageCategory;
    private final String unit;
    private final String description;
    private int initialPrice;
    private String productName;


    StockKeepUnit(int arrayId, String productName, SkuUsageClassification usageCategory, String unit, String description, int initialPrice) {
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
        return usageCategory.getPrintableName();
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }
}
