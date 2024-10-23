package com.saaenmadsen.shardworld.actors.shardcountry;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.saaenmadsen.shardworld.actors.countrymarket.C_StartMarketDayCycle;
import com.saaenmadsen.shardworld.actors.countrymarket.CountryMarket;
import com.saaenmadsen.shardworld.constants.WorldSettings;
import com.saaenmadsen.shardworld.actors.company.C_MarketOpenForSellers;
import com.saaenmadsen.shardworld.actors.company.ShardCompany;
import com.saaenmadsen.shardworld.modeltypes.PriceList;

import java.util.ArrayList;
import java.util.List;


/**
 * For both population and companies, there is a turn seqeunce like:
 * <p>
 * Import is actually just another company?? When importing a SKU for the first time, use the import prize +% as trade price.
 * Companies send their stock-for-sale to Country-market. This is like driving the truck with goods to market. *
 * Buy orders - including requests for goods not for sale
 * Trade completion
 * Country-market updates prices based on what was sold. If most was sold, more expensive etc.
 * Pricelist distribution. With pricelist comes the companies offering
 * Production
 * <p>
 * Trades are
 *
 * For now, I will NOT be using the FSM (Final State Machine) implementation pattern - crawl before walking and all that...
 */
public class CountryMainActor extends AbstractBehavior<CountryMainActor.CountryMainActorCommand> {
    private static WorldSettings worldSettings;
    private final int poolSize = 4;
    private final CountryStatisticsReceiver statsReceiver;



    private List<ActorRef<ShardCompany.ShardCompanyCommand>> allCompanies = new ArrayList<>();
    private ActorRef<CountryMarket.CountryMarketCommand> countryMarket;

    public interface CountryMainActorCommand{}

    public interface CountryStatisticsReceiver {
        void addDay(CountryDayStatistics stats);
    }

    public record CountryDayStatistics(
            int daySeqNo,
            int companyCount,
            int popCount,
            int[] pricelist
    ) {
    }

    public static Behavior<CountryMainActorCommand> create(WorldSettings worldSettings, CountryStatisticsReceiver statsReceiver) {
        CountryMainActor.worldSettings = worldSettings;

        return Behaviors.setup(
                context -> {
                    CountryMainActor countryActor = new CountryMainActor(context, statsReceiver);
                    return countryActor;
                });

    }

    public CountryMainActor(ActorContext<CountryMainActorCommand> context, CountryStatisticsReceiver statsReceiver) {
        super(context);
        this.statsReceiver = statsReceiver;
        getContext().getLog().info("ShardCountry Constructor Start");

        for(int i=0;i< worldSettings.companiesInCountry(); ++i) {
            String companyName = "company-" + i;
            allCompanies.add(context.spawn(ShardCompany.create(companyName), companyName));
        }
        countryMarket = context.spawn(CountryMarket.create(), "market");

        getContext().getLog().info("ShardCountry Constructor Completed");
    }

    @Override
    public Receive<CountryMainActorCommand> createReceive() {
        getContext().getLog().info("ShardCountry createReceive");
        return newReceiveBuilder()
                .onMessage(C_CountryDayStart.class, this::onNewDayStartReceived)
                .onMessage(C_EndMarketDayCycle.class, this::onEndMarketDayCycle)
                .build();
    }

    private Behavior<CountryMainActorCommand> onNewDayStartReceived(C_CountryDayStart message) {
        getContext().getLog().info("onNewDayStartReceived message received: {}", message);
        countryMarket.tell(new C_StartMarketDayCycle(message.dayId(), allCompanies));

        return Behaviors.same();
    }

    private Behavior<CountryMainActorCommand> onEndMarketDayCycle(C_EndMarketDayCycle message) {
        getContext().getLog().info("onEndMarketDayCycle order received: {}", message);

        return Behaviors.same();
    }

}
