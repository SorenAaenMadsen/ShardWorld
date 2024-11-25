package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.*;
import java.util.stream.Collectors;

public record WorldEndStatsWorld(@JsonIgnore StockListing finalWorldTotalStock,
                                 List<WorldEndStatsCountry> worldEndStatsCountries,
                                 PrintableStockListing finalWorldTotalStockMap) {

    public WorldEndStatsWorld(StockListing finalWorldTotalStock, List<WorldEndStatsCountry> worldEndStatsCountries) {
        this(finalWorldTotalStock, worldEndStatsCountries, new PrintableStockListing(finalWorldTotalStock));
    }


    /**
     * {
     *   "countries": ["USA", "Canada", "Germany", "France"],
     *   "skuData": [
     *     {"good": "Apples", "prices": [3.5, 2.7, 4.1, 3.9]},
     *     {"good": "Bananas", "prices": [1.2, 1.8, 2.5, 1.6]},
     *     {"good": "Oranges", "prices": [2.3, 2.9, 3.3, 2.8]}
     *   ]
     * }
     */
    @JsonIgnore
    public Map<String, Object> getPricesInAllCountriesAsDataPointsForWebGraph(String country, String usageCategory) {
        Map<String, Object> response = new HashMap<>();

        List<Map<String, Object>> skuData = new ArrayList<>();

        for (StockKeepUnit sku : StockKeepUnit.values()) {
            if(!usageCategory.isEmpty() && sku.getUsageCategory().equals(usageCategory)) {

                Map<String, Object> skuEntry = new HashMap<>();
                skuEntry.put("sku", sku.name());

                List<Integer> prices = new ArrayList<>();
                for (WorldEndStatsCountry worldEndStatsCountry : worldEndStatsCountries) {
                    if (worldEndStatsCountry.countryId().equals(country)) {
                        prices.add(worldEndStatsCountry.marketDayStats().marketDailyReport().getPriceListDayEnd().getPrice(sku.getArrayId()));
                    }
                }
                skuEntry.put("prices", prices);
                skuData.add(skuEntry);
            }
        }

        skuData.sort(Comparator.comparing(map -> (String) map.get("sku")));

        response.put("countries", worldEndStatsCountries.stream().map(c->c.getCountryId()).collect(Collectors.toUnmodifiableList()));
        response.put("skuData", skuData);
        return response;
    }


}

