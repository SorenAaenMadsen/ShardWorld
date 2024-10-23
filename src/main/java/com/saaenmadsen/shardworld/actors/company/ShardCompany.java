package com.saaenmadsen.shardworld.actors.company;

import akka.actor.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.countrymarket.C_SendSkuToMarketForSale;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.SkuStock;
import akka.actor.typed.javadsl.Adapter;

public class ShardCompany extends AbstractBehavior<ShardCompany.ShardCompanyCommand> {
    private String companyName;
    private SkuStock stock;

    public interface ShardCompanyCommand {
    }

    public static Behavior<ShardCompanyCommand> create(String companyName) {
        return Behaviors.setup(context -> new ShardCompany(context, companyName));
    }

    public ShardCompany(ActorContext<ShardCompanyCommand> context, String companyName) {
        super(context);
        this.companyName = companyName;
        this.stock = new SkuStock();
    }


    @Override
    public Receive<ShardCompanyCommand> createReceive() {
        getContext().getLog().info("ShardCompany createReceive");
        return newReceiveBuilder()
                .onMessage(C_MarketOpenForSellers.class, this::onReceiveMarketOpenForSellers)
                .onMessage(C_CompletedBuyOrder.class, this::onReceiveCompletedBuyOrder)
                .onMessage(C_MarketOpenForBuyers.class, this::onReceiveMarketOpenForBuyers)
                .build();
    }

    private Behavior<ShardCompanyCommand> onReceiveMarketOpenForSellers(C_MarketOpenForSellers message) {
        getContext().getLog().info(companyName + " got message {}", message.toString());
        ActorRef parent = Adapter.toClassic(getContext()).parent();

        // For now, just setting everything for sale.
        for (int i=0; i< StockKeepUnit.values().length; ++i) {
            int amount = stock.getStock(i);
            if(amount>0) {
                message.countryMarket().tell(new C_SendSkuToMarketForSale(i, amount));
            }
        }

        return Behaviors.same();
    }

    private Behavior<ShardCompanyCommand> onReceiveCompletedBuyOrder(C_CompletedBuyOrder message) {
        getContext().getLog().info(companyName + " got message {}", message.toString());

        return Behaviors.same();
    }

    private Behavior<ShardCompanyCommand> onReceiveMarketOpenForBuyers(C_MarketOpenForBuyers message) {
        getContext().getLog().info(companyName + " got message {}", message.toString());

        return Behaviors.same();
    }


}

