package com.saaenmadsen.shardworld.actors.company;

import akka.actor.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.countrymarket.C_BuyOrder;
import com.saaenmadsen.shardworld.actors.countrymarket.C_EndMarketDay;
import com.saaenmadsen.shardworld.actors.countrymarket.C_SendSkuToMarketForSale;
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
                .onMessage(C_SendUnsoldSkuBackToSeller.class, this::onSendUnsoldSkuBackToSeller)
                .build();
    }

    private Behavior<ShardCompanyCommand> onSendUnsoldSkuBackToSeller(C_SendUnsoldSkuBackToSeller message) {
        getContext().getLog().info(companyName + " got message {}", message.toString());
        message.market().tell(new C_EndMarketDay(this.companyName));
        return Behaviors.same();
    }

    private Behavior<ShardCompanyCommand> onReceiveMarketOpenForSellers(C_MarketOpenForSellers message) {
        getContext().getLog().info(companyName + " got message {}", message.toString());
        ActorRef parent = Adapter.toClassic(getContext()).parent();

        doProduction();
        sendSkuItemsForSaleToMarket(message);

        return Behaviors.same();
    }

    private void doProduction() {
        stock.setSkuCount(0, 31);
        stock.setSkuCount(1, 12);
    }

    private void sendSkuItemsForSaleToMarket(C_MarketOpenForSellers message) {
        // For now, just setting everything for sale.
        SkuStock forSaleList = stock.retrieve(stock);
        message.countryMarket().tell(new C_SendSkuToMarketForSale(forSaleList, getContext().getSelf()));
    }

    private Behavior<ShardCompanyCommand> onReceiveCompletedBuyOrder(C_CompletedBuyOrder message) {
        getContext().getLog().info(companyName + " got message {}", message.toString());

        return Behaviors.same();
    }

    private Behavior<ShardCompanyCommand> onReceiveMarketOpenForBuyers(C_MarketOpenForBuyers message) {
        getContext().getLog().info(companyName + " got message {}", message.toString());
        SkuStock buyList = new SkuStock();
        message.countryMarket().tell(new C_BuyOrder(buyList, getContext().getSelf()) );
        return Behaviors.same();
    }


}

