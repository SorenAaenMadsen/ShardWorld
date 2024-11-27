package com.saaenmadsen.shardworld.actors.shardcountry;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.company.A_ShardCompany;
import com.saaenmadsen.shardworld.actors.company.CompanyInformation;
import com.saaenmadsen.shardworld.actors.company.CompanyInformationBuilder;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyCultureBuilder;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyCulture_InnovativenessLevel;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyCulture_StockManagementLevel;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyType;
import com.saaenmadsen.shardworld.actors.company.workers.EmployeeGroup;
import com.saaenmadsen.shardworld.actors.countrymarket.A_CountryMarket;
import com.saaenmadsen.shardworld.actors.countrymarket.C_StartMarketDayCycle;
import com.saaenmadsen.shardworld.actors.popgroup.A_PopGroup;
import com.saaenmadsen.shardworld.actors.shardworld.A_ShardWorld;
import com.saaenmadsen.shardworld.actors.shardworld.C_WorldDayEnd;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettings;
import com.saaenmadsen.shardworld.statistics.CompanyDayStats;
import com.saaenmadsen.shardworld.statistics.CountryDayStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * For both population and companies, there is a turn seqeunce like:
 * <p>
 * Import is actually just another company?? When importing a SKU for the first time, use the import prize +% as trade price.
 * Companies send their wishList-for-sale to Country-market. This is like driving the truck with goods to market. *
 * Buy orders - including requests for goods not for sale
 * Trade completion
 * Country-market updates prices based on what was sold. If most was sold, more expensive etc.
 * Pricelist distribution. With pricelist comes the companies offering
 * Production
 * <p>
 * Trades are
 * <p>
 * For now, I will NOT be using the FSM (Final State Machine) implementation pattern - crawl before walking and all that...
 */
public class A_ShardCountry extends AbstractBehavior<A_ShardCountry.CountryMainActorCommand> {
    private int popGroupId = 0;
    private WorldSettings worldSettings;
    private final ActorRef<A_ShardWorld.WorldCommand> worldActorReference;
    private final String countryId;

    private List<ActorRef<A_ShardCompany.ShardCompanyCommand>> allCompanies = new ArrayList<>();
    private List<ActorRef<A_PopGroup.PopGroupCommand>> allPopGroups = new ArrayList<>();
    private ActorRef<A_CountryMarket.CountryMarketCommand> countryMarket;

    List<C_CompanyDayEnd> companyDayEnds = new ArrayList<>();
    Optional<C_EndMarketDayCycle> endMarketDayCycle = Optional.empty();


    public interface CountryMainActorCommand {
    }

    public static Behavior<CountryMainActorCommand> create(String countryId, WorldSettings worldSettings, ActorRef<A_ShardWorld.WorldCommand> worldActorReference) {
        return Behaviors.setup(
                context -> {
                    A_ShardCountry countryActor = new A_ShardCountry(countryId, context, worldSettings, worldActorReference);
                    return countryActor;
                });

    }

    public A_ShardCountry(String countryId, ActorContext<CountryMainActorCommand> context, WorldSettings worldSettings, ActorRef<A_ShardWorld.WorldCommand> worldActorReference) {
        super(context);
        this.countryId = countryId;
        this.worldSettings = worldSettings;
        this.worldActorReference = worldActorReference;
        getContext().getLog().info("ShardCountry Constructor Start");

        // Spawn a strategic stockpile, which the player will later control
        CompanyInformation strategicStockpile = CompanyInformationBuilder
                .ofWorldDefault("1-strategic-stockpile", worldSettings)
                .withCulture(CompanyCultureBuilder.of(CompanyCulture_InnovativenessLevel.LOW_INNOVATIVE, CompanyCulture_StockManagementLevel.HIGH_STOCK_BUFFER).build())
                .withCompanyType(CompanyType.STOCKPILE)
                .withMoney(10000000)
                .build();
        spawnCompanyAndPopGroupActors_AddWorkerPopLinkToCompanyInformation(context, worldSettings, strategicStockpile);


        // Spawn production companies
        List<CompanyInformation> myStartCompanies = new ArrayList<>();
        myStartCompanies.addAll(worldSettings.startCompanies());
        for (int i = myStartCompanies.size(); i < this.worldSettings.companyCount(); ++i) {
            String companyName = "company-" + String.format("%05d", i);
            myStartCompanies.add(CompanyInformationBuilder.ofWorldDefault(companyName, worldSettings).build());
        }

        for (CompanyInformation companyInformation : myStartCompanies ) {
            spawnCompanyAndPopGroupActors_AddWorkerPopLinkToCompanyInformation(context, worldSettings, companyInformation);
        }

        // Spawn country market
        countryMarket = context.spawn(A_CountryMarket.create(context.getSelf(), worldSettings), "market");

        getContext().getLog().info("ShardCountry Constructor Completed");
    }

    private void spawnCompanyAndPopGroupActors_AddWorkerPopLinkToCompanyInformation(ActorContext<CountryMainActorCommand> context, WorldSettings worldSettings, CompanyInformation companyInformation) {
        for (EmployeeGroup employeeGroup : companyInformation.getEmployeeGroups()) {
            String popGroupName = this.countryId + "-PopGroup-" + this.popGroupId++;
            Behavior<A_PopGroup.PopGroupCommand> popGroupCommandBehavior = A_PopGroup.create(popGroupName, employeeGroup.getPopCount(), employeeGroup.getEmployeeCategory(), context.getSelf(), worldSettings);

            ActorRef<A_PopGroup.PopGroupCommand> popGroupActor = context.spawn(popGroupCommandBehavior, popGroupName);
            employeeGroup.setPopGroupRef(popGroupActor);
            allPopGroups.add(popGroupActor);
        }

        allCompanies.add(
                context.spawn(
                        A_ShardCompany.create(companyInformation, context.getSelf(), worldSettings),
                        companyInformation.getCompanyId()
                )
        );
    }

    @Override
    public Receive<CountryMainActorCommand> createReceive() {
        getContext().getLog().info("Country createReceive");
        return newReceiveBuilder()
                .onMessage(C_CountryDayStart.class, this::onNewDayStartReceived)
                .onMessage(C_EndMarketDayCycle.class, this::onEndMarketDayCycle)
                .onMessage(C_CompanyDayEnd.class, this::onCompanyDayEnd)
                .build();
    }

    private Behavior<CountryMainActorCommand> onNewDayStartReceived(C_CountryDayStart message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Country onNewDayStartReceived message received: {}", message);
        }
        companyDayEnds = new ArrayList<>();
        endMarketDayCycle = Optional.empty();
        countryMarket.tell(new C_StartMarketDayCycle(message.dayId(), allCompanies, allPopGroups));
        return Behaviors.same();
    }


    private Behavior<CountryMainActorCommand> onCompanyDayEnd(C_CompanyDayEnd message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Country onCompanyDayEnd message received: {}", message);
        }
        companyDayEnds.add(message);
        checkEndCountryDay();
        return Behaviors.same();
    }

    private Behavior<CountryMainActorCommand> onEndMarketDayCycle(C_EndMarketDayCycle message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Country onEndMarketDayCycle order received: {}", message);
        }
        endMarketDayCycle = Optional.of(message);
        checkEndCountryDay();
        return Behaviors.same();
    }

    private void checkEndCountryDay() {
        if (endMarketDayCycle.isPresent() && companyDayEnds.size() == allCompanies.size()) {
            CompanyDayStats[] companyDayStatsArray = companyDayEnds.stream().map(message -> message.companyDayStats()).toArray(CompanyDayStats[]::new);
            CountryDayStats countryDayStats = new CountryDayStats(endMarketDayCycle.get().dayId(), this.countryId, companyDayStatsArray, endMarketDayCycle.get().marketDayStats());
            worldActorReference.tell(new C_WorldDayEnd(endMarketDayCycle.get().dayId(), countryDayStats));
        }
    }

}
