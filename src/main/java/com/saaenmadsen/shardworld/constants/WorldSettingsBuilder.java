package com.saaenmadsen.shardworld.constants;

import com.saaenmadsen.shardworld.actors.company.A_ShardCompany;
import com.saaenmadsen.shardworld.actors.company.CompanyInformation;

import java.util.ArrayList;
import java.util.List;

public class WorldSettingsBuilder {

    private int companyCount;
    private int countryCount;
    private int maxDaysToRun;
    private boolean logAkkaMessages;
    private int companyInitialMoney;
    private List<CompanyInformation> startCompanies;


    public WorldSettingsBuilder(int companyCount, int countryCount, int maxDaysToRun, boolean logAkkaMessages, int companyInitialMoney, List<CompanyInformation> startCompanies) {
        this.companyCount = companyCount;
        this.countryCount = countryCount;
        this.maxDaysToRun = maxDaysToRun;
        this.logAkkaMessages = logAkkaMessages;
        this.companyInitialMoney = companyInitialMoney;
        this.startCompanies = startCompanies;
    }

    public static WorldSettingsBuilder ofDefault() {
        return new WorldSettingsBuilder(
                10,
                1,
                10,
                false,
                100000,
                new ArrayList<>()
        );
    }

    public WorldSettingsBuilder withStartCompany(CompanyInformation company) {
        this.startCompanies.add(company);
        return this;
    }

    public WorldSettingsBuilder withDaysToRun(int daysToRun) {
        this.maxDaysToRun = daysToRun;
        return this;
    }

    public WorldSettingsBuilder withOnlyTheManuallyAddedCompanies() {
        this.companyCount = this.startCompanies.size();
        return this;
    }

    public WorldSettings build() {
        return new WorldSettings(
                companyCount,
                countryCount,
                maxDaysToRun,
                logAkkaMessages,
                companyInitialMoney,
                startCompanies
        );
    }

    public WorldSettingsBuilder withAkkaMessageLogging() {
        this.logAkkaMessages = true;
        return this;
    }
}
