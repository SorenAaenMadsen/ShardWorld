package com.saaenmadsen.shardworld.statistics;

public record CompanyDayStats(String dailyReport,
                              java.util.List<com.saaenmadsen.shardworld.actors.company.KnownRecipe> companyRecipes,
                              com.saaenmadsen.shardworld.modeltypes.StockListing unsoldGoods) {
}
