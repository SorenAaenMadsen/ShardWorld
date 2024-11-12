package com.saaenmadsen.shardworld.actors.company.direction;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyDailyReport;

public class FoundingBoardMeeting {
    public FoundingBoardMeeting(CompanyInformation companyInformation, CompanyDailyReport companyDailyReport) {
        new InnovateOurRecipiesBoardMeeting(companyInformation, companyDailyReport);
        new InnovateOurRecipiesBoardMeeting(companyInformation, companyDailyReport);
        new InnovateOurRecipiesBoardMeeting(companyInformation, companyDailyReport);
    }

}
