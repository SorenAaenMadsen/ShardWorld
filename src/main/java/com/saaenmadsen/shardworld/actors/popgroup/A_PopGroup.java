package com.saaenmadsen.shardworld.actors.popgroup;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.company.*;
import com.saaenmadsen.shardworld.actors.company.culture.CompanyType;
import com.saaenmadsen.shardworld.actors.company.direction.*;
import com.saaenmadsen.shardworld.actors.countrymarket.C_BuyOrder;
import com.saaenmadsen.shardworld.actors.countrymarket.C_EndMarketDay;
import com.saaenmadsen.shardworld.actors.countrymarket.C_SendSkuToMarketForSale;
import com.saaenmadsen.shardworld.actors.shardcountry.A_ShardCountry;
import com.saaenmadsen.shardworld.actors.shardcountry.C_CompanyDayEnd;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettings;
import com.saaenmadsen.shardworld.modeltypes.StockListing;
import com.saaenmadsen.shardworld.statistics.CompanyDayStats;
import com.saaenmadsen.shardworld.statistics.PrintableStockListing;

public class A_PopGroup extends AbstractBehavior<A_PopGroup.PopGroupCommand> {
    private int popCount;
    private int ageDistribution[];

    private final ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor;
    private final WorldSettings worldSettings;


    public interface PopGroupCommand {
    }

    public static Behavior<PopGroupCommand> create(int popCount, ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        return Behaviors.setup(context -> new A_PopGroup(context, popCount, countryActor, worldSettings));
    }

    public A_PopGroup(ActorContext<PopGroupCommand> context, int popCount, ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        super(context);
        getContext().getLog().debug("PopGroup Constructor start");
        this.popCount = popCount;
        this.countryActor = countryActor;
        this.worldSettings = worldSettings;

        getContext().getLog().debug("PopGroup Constructor done");
    }


    @Override
    public Receive<PopGroupCommand> createReceive() {
        getContext().getLog().debug("ShardCompany createReceive");
        return newReceiveBuilder()
                .onMessage(C_MarketOpenForBuyers.class, this::onMarketOpenForBuyers)
                .build();
    }


    // ****************** Acions *************************

    private Behavior<PopGroupCommand> onMarketOpenForBuyers(C_MarketOpenForBuyers cMarketOpenForBuyers) {
        return null;
    }

}

