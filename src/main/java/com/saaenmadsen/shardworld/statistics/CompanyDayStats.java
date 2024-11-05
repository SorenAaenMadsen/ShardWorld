package com.saaenmadsen.shardworld.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaenmadsen.shardworld.actors.company.KnownRecipe;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.List;

public record CompanyDayStats(String dailyReport,
                              java.util.List<KnownRecipe> companyRecipes,
                              @JsonIgnore StockListing unsoldGoods,
                              @JsonIgnore StockListing companyWarehouse,
                              PrintableStockListing companyWarehouseMap,
                              PrintableStockListing unsoldGoodsMap) {

    public CompanyDayStats(String dailyReport, List<KnownRecipe> companyRecipes, StockListing unsoldGoods, StockListing companyWarehouse) {
        this(dailyReport, companyRecipes, unsoldGoods, companyWarehouse, new PrintableStockListing(companyWarehouse), new PrintableStockListing(unsoldGoods));
    }

}
