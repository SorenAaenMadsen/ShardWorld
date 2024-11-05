package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.DailyReport;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class DesiceWhatToSellAtMarket {

    private final StockListing forSaleList;

    public DesiceWhatToSellAtMarket(CompanyInformation companyInformation, DailyReport dailyReport) {
        this.forSaleList = justSetItAllForSale(companyInformation, dailyReport);
    }

    private StockListing justSetItAllForSale(CompanyInformation companyInformation, DailyReport dailyReport) {
        StockListing forSaleList = companyInformation.getWarehouse().retrieve(companyInformation.getWarehouse());
        dailyReport.setForSaleList(forSaleList);
        return forSaleList;
    }

    public StockListing getForSaleList() {
        return forSaleList;
    }
}
