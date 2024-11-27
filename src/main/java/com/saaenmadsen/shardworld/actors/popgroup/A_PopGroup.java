package com.saaenmadsen.shardworld.actors.popgroup;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.saaenmadsen.shardworld.actors.company.workers.EmployeeCategory;
import com.saaenmadsen.shardworld.actors.countrymarket.C_BuyPopOrder;
import com.saaenmadsen.shardworld.actors.popgroup.decisions.PopPurchaseDecision;
import com.saaenmadsen.shardworld.actors.shardcountry.A_ShardCountry;
import com.saaenmadsen.shardworld.constants.worldsettings.WorldSettings;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class A_PopGroup extends AbstractBehavior<A_PopGroup.PopGroupCommand> {
    private final String popGroupName;
    private EmployeeCategory employeeCategory;
    private int popCount;
    private int ageDistribution[];
    private MoneyBox moneyBox;

    private final ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor;
    private final WorldSettings worldSettings;


    public interface PopGroupCommand {
    }

    public static Behavior<PopGroupCommand> create(String popGroupName, int popCount, EmployeeCategory employeeCategory, ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        return Behaviors.setup(context -> new A_PopGroup(context, popGroupName, popCount, employeeCategory, countryActor, worldSettings));
    }

    public A_PopGroup(ActorContext<PopGroupCommand> context, String popGroupName, int popCount, EmployeeCategory employeeCategory, ActorRef<A_ShardCountry.CountryMainActorCommand> countryActor, WorldSettings worldSettings) {
        super(context);
        this.popGroupName = popGroupName;
        this.employeeCategory = employeeCategory;
        getContext().getLog().debug("PopGroup " + popGroupName + " Constructor start");
        this.popCount = popCount;
        this.countryActor = countryActor;
        this.worldSettings = worldSettings;
        this.moneyBox = new MoneyBox();

        getContext().getLog().debug("PopGroup " + popGroupName + " Constructor done");
    }


    @Override
    public Receive<PopGroupCommand> createReceive() {
        getContext().getLog().debug("ShardCompany " + popGroupName + " createReceive");
        return newReceiveBuilder()
                .onMessage(C_PaySalary.class, this::onPaySalary)
                .onMessage(C_MarketOpenForPopBuyers.class, this::onMarketOpenForBuyers)
                .onMessage(C_CompletedPopBuyOrder.class, this::onCompletedPopBuyOrder)
                .build();
    }


    // ****************** Actions *************************

    private Behavior<PopGroupCommand> onPaySalary(C_PaySalary message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Popgroup " + popGroupName + " is paid {}", message.toString());
        }
        moneyBox.mergeMoneyBox(message.safeBoxWithSalary());
        return Behaviors.same();
    }

    private Behavior<PopGroupCommand> onMarketOpenForBuyers(C_MarketOpenForPopBuyers message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Market is open for popgroup " + popGroupName + " {}", message.toString());
        }
        StockListing shoppingList = PopPurchaseDecision.makeDecision();

        message.countryMarket().tell(
                new C_BuyPopOrder(
                        shoppingList,
                        moneyBox.newBoxWithAllTheMoeny(),
                        getContext().getSelf(),
                        this.popGroupName
                )
        );

        return Behaviors.same();
    }

    private Behavior<PopGroupCommand> onCompletedPopBuyOrder(C_CompletedPopBuyOrder message) {
        if (worldSettings.logAkkaMessages()) {
            getContext().getLog().info("Buy order complete for Popgroup " + popGroupName + " {}", message.toString());
        }
        return Behaviors.same();
    }
}

