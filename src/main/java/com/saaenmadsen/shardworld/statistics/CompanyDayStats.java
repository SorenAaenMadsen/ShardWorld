package com.saaenmadsen.shardworld.statistics;

import com.saaenmadsen.shardworld.modeltypes.StockListing;

public record CompanyDayStats(String dailyReport,
                              java.util.List<com.saaenmadsen.shardworld.actors.company.KnownRecipe> companyRecipes,
                              com.saaenmadsen.shardworld.modeltypes.StockListing unsoldGoods,
                              StockListing companyWarehouse) {
}
