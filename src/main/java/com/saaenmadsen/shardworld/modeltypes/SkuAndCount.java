package com.saaenmadsen.shardworld.modeltypes;

import com.saaenmadsen.shardworld.constants.StockKeepUnit;

import java.util.Optional;

/**

 Furnace Type	Period	Temperature Range	Output Type
 Bloomery Furnace	c. 1200 BCE onward	1,200–1,300°C	Wrought Iron
 Early Shaft Furnace	Iron Age (c. 5th century BCE)	1,300–1,500°C	Wrought and Cast Iron
 Chinese Blast Furnace	Han Dynasty (c. 200 BCE)	1,500–1,600°C	Cast Iron
 Medieval European Blast Furnace	12th–15th Century CE	1,600°C	Cast Iron
 Puddling Furnace	18th Century	1,350–1,450°C	Wrought Iron
 Bessemer Converter	1856	1,600–1,700°C	Low-Carbon Steel
 Electric Arc Furnace	20th Century	1,800°C+	Steel and Alloys

 */

public record SkuAndCount(StockKeepUnit sku, int amount) {
    public static Optional<SkuAndCount> fromStrings(String productName, String amount) {
        if (productName.isEmpty()) return Optional.empty();
        if (productName.equals("Null")) return Optional.empty();
        return Optional.of(new SkuAndCount(StockKeepUnit.getByProductName(productName), Integer.parseInt(amount)));
    }

    public static Optional<SkuAndCount> optionalOf(StockKeepUnit sku, int amount) {
        if (null == sku) return Optional.empty();
        return Optional.of(new SkuAndCount(sku, amount));
    }

    public static SkuAndCount of(StockKeepUnit sku, int amount) {
        if (null == sku) throw new IllegalArgumentException("sku is null");
        return new SkuAndCount(sku, amount);
    }

    @Override
    public String toString() {
        return "SkuAndCount{" +
                "skuId=" + sku.getArrayId() +
                "productName=" + sku.getProductName() +
                ", amount=" + amount +
                '}';
    }
}
