package com.saaenmadsen.shardworld.constants;

import com.saaenmadsen.shardworld.actors.company.A_ShardCompany;

import java.util.List;

public record WorldSettings(
        int companyCount,
        int countryCount,
        int maxDaysToRun,
        boolean logAkkaMessages,
        int companyInitialMoney,
        List<A_ShardCompany> startCompanies
) {


}
