package com.saaenmadsen.shardworld.shardcountry;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.*;
import com.saaenmadsen.shardworld.shardcountry.company.ShardCompany;
import com.saaenmadsen.shardworld.shardcountry.company.SellCommand;


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
public class CountryMainActor extends AbstractBehavior<SellCommand> {
    private final int poolSize = 4;
    private final ActorSystem<SellCommand> ourOutbox;

    private PoolRouter<SellCommand> pool;
    private ActorRef<SellCommand> router;

    public static Behavior<SellCommand> create() {
//        return Behaviors.setup(Kitchen::new);
        return Behaviors.setup(
                context -> {
                    CountryMainActor kitchen = new CountryMainActor(context);
                    return kitchen;
                });

    }

    public CountryMainActor(ActorContext<SellCommand> context) {
        super(context);
        ourOutbox = ActorSystem.create(ShardCompany.create(), "AzureMessageSender");
        pool =
                Routers.pool(
                        poolSize,
                        // make sure the workers are restarted if they fail
                        Behaviors.supervise(ShardCompany.create()).onFailure(SupervisorStrategy.restart()));
        router = context.spawn(pool, "company-pool");

        getContext().getLog().info("Kitchen Constructor Completed");
    }

    @Override
    public Receive<SellCommand> createReceive() {
        getContext().getLog().info("Kitchen createReceive");
        return newReceiveBuilder().onMessage(SellCommand.class, this::onNewOrderReceived).build();
    }

    private Behavior<SellCommand> onNewOrderReceived(SellCommand incomingOrder) {
        getContext().getLog().info("New order received for {}", incomingOrder);
        router.tell(incomingOrder);
        return Behaviors.same();
    }

//    public get

}
