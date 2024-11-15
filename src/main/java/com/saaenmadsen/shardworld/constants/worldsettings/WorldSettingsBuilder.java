package com.saaenmadsen.shardworld.constants.worldsettings;

import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyInformationBuilder;

import java.util.ArrayList;
import java.util.List;

public class WorldSettingsBuilder {

    private int companyCount;
    private int countryCount;
    private int maxDaysToRun;
    private boolean logAkkaMessages;
    private int companyInitialMoney;
    private List<CompanyInformation> startCompanies;
    private WorldRunMode worldRunMode;


    public WorldSettingsBuilder(int companyCount, int countryCount, int maxDaysToRun, boolean logAkkaMessages, int companyInitialMoney, List<CompanyInformation> startCompanies, WorldRunMode worldRunMode) {
        this.companyCount = companyCount;
        this.countryCount = countryCount;
        this.maxDaysToRun = maxDaysToRun;
        this.logAkkaMessages = logAkkaMessages;
        this.companyInitialMoney = companyInitialMoney;
        this.startCompanies = startCompanies;
        this.worldRunMode = worldRunMode;
    }

    public static WorldSettingsBuilder ofDefault() {
        return new WorldSettingsBuilder(
                10,
                1,
                10,
                false,
                100000,
                new ArrayList<>(),
                WorldRunMode.AUTO_RUN_TO_END
        );
    }

    public WorldSettingsBuilder withStartCompany(CompanyInformation company) {
        this.startCompanies.add(company);
        return this;
    }

    public WorldSettingsBuilder withMaxDaysToRun(int daysToRun) {
        this.maxDaysToRun = daysToRun;
        return this;
    }

    public WorldSettingsBuilder withNumberOfCompaniesPrCountry(int companyCount) {
        this.companyCount = companyCount;
        return this;
    }

    public WorldSettingsBuilder withShardCountryCount(int countryCount) {
        this.countryCount = countryCount;
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
                startCompanies,
                worldRunMode
        );
    }

    public WorldSettingsBuilder withAkkaMessageLogging() {
        this.logAkkaMessages = true;
        return this;
    }

    public WorldSettingsBuilder withWorldRunMode(WorldRunMode worldRunMode) {
        this.worldRunMode = worldRunMode;
        return this;
    }

    public WorldSettingsBuilder withCompanyInitialMoney(int amount) {
        this.companyInitialMoney = amount;
        return this;
    }
}
