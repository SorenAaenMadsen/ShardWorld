package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class DesideWhatToSellAtMarket {

    private final StockListing forSaleList;

    public DesideWhatToSellAtMarket(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        this.forSaleList = justSetItAllForSale(companyInformation, companyDailyReport);
    }

    private StockListing justSetItAllForSale(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        StockListing forSaleList = companyInformation.getWarehouse().retrieve(companyInformation.getWarehouse());
        companyDailyReport.setForSaleList(forSaleList);
        return forSaleList;
    }

    public StockListing getForSaleList() {
        return forSaleList;
    }
}
