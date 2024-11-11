package com.saaenmadsen.shardworld.constants.worldsettings;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;

import java.util.List;

public record WorldSettings(
        int companyCount,
        int countryCount,
        int maxDaysToRun,
        boolean logAkkaMessages,
        int companyInitialMoney,
        List<CompanyInformation> startCompanies,
        WorldRunMode worldRunMode
) {


}
