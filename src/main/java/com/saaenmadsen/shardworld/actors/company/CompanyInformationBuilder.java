package com.saaenmadsen.shardworld.actors.company;

import com.saaenmadsen.shardworld.actors.company.culture.CompanyCulture;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyCultureBuilder;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyType;
import com.saaenmadsen.shardworld.actors.company.flawor.CompanyFlawor;
import com.saaenmadsen.shardworld.actors.company.workers.EmployeeCategory;
import com.saaenmadsen.shardworld.actors.company.workers.EmployeeGroup;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettings;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompanyInformationBuilder {
    private String companyId;
    private CompanyCulture culture;
    private CompanyFlawor companyFlawor;
    private StockListing warehouse;
    private List<KnownRecipe> myRecipes;
    private List<EmployeeGroup> employeeGroups;
    private Random dice;
    private PriceList priceList;
    private MoneyBox moneyBox;
    CompanyType companyType;

    private CompanyInformationBuilder(String companyId, WorldSettings worldSettings) {
        this.companyId = companyId;
        this.culture = CompanyCultureBuilder.ofRandom().build();
        this.warehouse = StockListing.ofEmpty();
        this.dice = new Random();
        this.myRecipes = new ArrayList<>();
        this.priceList = PriceList.ofDefault();
        this.companyFlawor = new CompanyFlawor();
        this.moneyBox = new MoneyBox();
        moneyBox.addMoney(worldSettings.companyInitialMoney());
        this.companyType = CompanyType.PRODUCTION;
        this.employeeGroups = List.of(EmployeeGroup.of(EmployeeCategory.WORKER, 9), EmployeeGroup.of(EmployeeCategory.OWNER, 1));
    }

    private CompanyInformationBuilder(
            String companyId,
            CompanyCulture culture,
            CompanyFlawor companyFlawor,
            StockListing warehouse,
            List<KnownRecipe> myRecipes,
            List<EmployeeGroup> employeeGroups,
            Random dice,
            PriceList priceList,
            MoneyBox moneyBox) {
        this.companyId = companyId;
        this.culture = culture;
        this.companyFlawor = companyFlawor;
        this.warehouse = warehouse;
        this.myRecipes = myRecipes;
        this.employeeGroups = employeeGroups;
        this.dice = dice;
        this.priceList = priceList;
        this.moneyBox = moneyBox;
    }

    public static CompanyInformationBuilder ofWorldDefault(String companyId, WorldSettings worldSettings) {
        if (companyId == null || !companyId.matches("[-a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("companyId must contain only letters and numbers, with no spaces or special characters. Was " + companyId);
        }
        return new CompanyInformationBuilder(companyId, worldSettings);
    }

    public static CompanyInformationBuilder ofManual(String companyId) {
        MoneyBox moneyBox = new MoneyBox();
        moneyBox.addMoney(0);

        return new CompanyInformationBuilder(
                companyId,
                CompanyCultureBuilder.ofRandom().build(),
                new CompanyFlawor(),
                StockListing.ofEmpty(),
                new ArrayList<>(),
                List.of(EmployeeGroup.of(EmployeeCategory.WORKER, 9), EmployeeGroup.of(EmployeeCategory.OWNER, 1)),
                new Random(),
                PriceList.ofDefault(),
                moneyBox
        );
    }

    public CompanyInformationBuilder withMoney(long money) {
        this.moneyBox = new MoneyBox();
        this.moneyBox.addMoney(money);
        return this;
    }

    public CompanyInformationBuilder withCulture(CompanyCulture culture) {
        this.culture = culture;
        return this;
    }

    public CompanyInformationBuilder withWorkers(List<EmployeeGroup> employeeGroups) {
        this.employeeGroups = employeeGroups;
        return this;
    }

    public CompanyInformationBuilder withKnownRecipe(KnownRecipe knownRecipe) {
        this.myRecipes.add(knownRecipe);
        return this;
    }

    public CompanyInformationBuilder withCompanyType(CompanyType companyType) {
        this.companyType = companyType;
        return this;
    }

    public CompanyInformationBuilder withWarehouse(StockListing warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public CompanyInformation build() {
        return new CompanyInformation(
                companyId,
                companyType,
                culture,
                companyFlawor,
                warehouse,
                myRecipes,
                employeeGroups,
                dice,
                priceList,
                moneyBox
        );
    }
}
