package com.saaenmadsen.shardworld.actors.shardcountry;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.*;
import com.saaenmadsen.shardworld.actors.company.ShardCompany;
import com.saaenmadsen.shardworld.actors.countrymarket.C_SellOrder;


/**
 *
 * For both population and companies, there is a turn seqeunce like:
 *
 * Import is actually just another company?? When importing a SKU for the first time, use the import prize +% as trade price.
 *  Companies send their stock-for-sale to Country-market. This is like driving the truck with goods to market. *
 *  Buy orders - including requests for goods not for sale
 *  Trade completion
 *  Country-market updates prices based on what was sold. If most was sold, more expensive etc.
 *  Pricelist distribution. With pricelist comes the companies offering
 *  Production
 *
 * Trades are
 *
 */
public class CountryMainActor extends AbstractBehavior<CountryDayStartCommand> {
    private final int poolSize = 4;

    private PoolRouter<C_SellOrder> allCompaniesPool;
    private ActorRef<C_SellOrder> randomCompaniesProxy;

    public static Behavior<CountryDayStartCommand> create() {

        return Behaviors.setup(
                context -> {
                    CountryMainActor countryActor = new CountryMainActor(context);
                    return countryActor;
                });

    }

    public CountryMainActor(ActorContext<CountryDayStartCommand> context) {
        super(context);
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
    public Receive<CountryDayStartCommand> createReceive() {
        getContext().getLog().info("ShardCountry createReceive");
        return newReceiveBuilder().onMessage(CountryDayStartCommand.class, this::onNewDayStartReceived).build();
    }

    private Behavior<CountryDayStartCommand> onNewDayStartReceived(CountryDayStartCommand incomingOrder) {
        getContext().getLog().info("New order received for {}", incomingOrder);
        randomCompaniesProxy.tell(new C_SellOrder(1,1));
        return Behaviors.same();
    }

}
