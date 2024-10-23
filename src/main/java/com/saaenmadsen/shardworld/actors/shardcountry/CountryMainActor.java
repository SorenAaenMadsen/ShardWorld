package com.saaenmadsen.shardworld.actors.shardcountry;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.*;
import com.saaenmadsen.shardworld.actors.company.C_MarketOpenForSellers;
import com.saaenmadsen.shardworld.actors.company.ShardCompany;



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
    private final int poolSize = 4;
    private final CountryStatisticsReceiver statsReceiver;

    private PoolRouter<ShardCompany.ShardCompanyCommand> allCompaniesPool;
    private ActorRef<ShardCompany.ShardCompanyCommand> randomCompaniesProxy;

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

    public static Behavior<CountryMainActorCommand> create(CountryStatisticsReceiver statsReceiver) {

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
        allCompaniesPool =
                Routers.pool(
                        poolSize,
                        // make sure the workers are restarted if they fail
                        Behaviors.supervise(ShardCompany.create()).onFailure(SupervisorStrategy.restart()));
        randomCompaniesProxy = context.spawn(allCompaniesPool, "company-pool");

        getContext().getLog().info("ShardCountry Constructor Completed");
    }

    @Override
    public Receive<CountryMainActorCommand> createReceive() {
        getContext().getLog().info("ShardCountry createReceive");
        return newReceiveBuilder().onMessage(CountryMainActorCommand.class, this::onNewDayStartReceived).build();
    }

    private Behavior<CountryMainActorCommand> onNewDayStartReceived(CountryMainActorCommand incomingOrder) {
        getContext().getLog().info("New order received for {}", incomingOrder);
        randomCompaniesProxy.tell(new C_MarketOpenForSellers(1));
        return Behaviors.same();
    }

}
