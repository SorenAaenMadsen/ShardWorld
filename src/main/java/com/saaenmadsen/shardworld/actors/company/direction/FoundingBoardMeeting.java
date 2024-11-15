package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class FoundingBoardMeeting {
    public FoundingBoardMeeting(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        InnovateOurRecipiesBoardMeeting.investInNewCompletelyRandomProductionRecipe(companyInformation, companyDailyReport);
        new InnovateOurRecipiesBoardMeeting(companyInformation, companyDailyReport, StockListing.ofEmpty());
        new InnovateOurRecipiesBoardMeeting(companyInformation, companyDailyReport, StockListing.ofEmpty());
    }

}
