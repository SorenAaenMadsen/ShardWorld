package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class FoundingBoardMeeting {
    public FoundingBoardMeeting(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        InnovateOurRecipesBoardMeeting.investInNewCompletelyRandomProductionRecipe(companyInformation, companyDailyReport);
        new InnovateOurRecipesBoardMeeting(companyInformation, companyDailyReport, StockListing.ofEmpty());
        new InnovateOurRecipesBoardMeeting(companyInformation, companyDailyReport, StockListing.ofEmpty());
    }

}
