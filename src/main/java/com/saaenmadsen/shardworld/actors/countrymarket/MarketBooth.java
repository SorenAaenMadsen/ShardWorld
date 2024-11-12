package com.saaenmadsen.shardworld.actors.countrymarket;

import akka.actor.typed.ActorRef;
import com.saaenmadsen.shardworld.actors.company.A_ShardCompany;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import com.saaenmadsen.shardworld.modeltypes.PriceList;
import com.saaenmadsen.shardworld.modeltypes.StockListing;

public class MarketBooth {
    MoneyBox boothMoney;
    StockListing boothStock;
    private final ActorRef<A_ShardCompany.ShardCompanyCommand> seller;
    private boolean isOpen;

    public MarketBooth(StockListing stockListing, ActorRef<A_ShardCompany.ShardCompanyCommand> seller) {
        this.boothStock = stockListing;
        this.seller = seller;
        this.boothMoney = new MoneyBox();
    }

    /**
     * The customer comes with his shopping list, and his cart. We fulfill what we can, and he will continue his merry way.
     * This also transfers money in the opposite direction.
     * Only allow purchaser to buy what can be afforded.
     * @param shoppingCart The customer may already have purchases in his shopping cart from other shops. This also holds the shopping money.
     * @param shoppingList This is the customers absolute needs.
     * @param priceList current prices at the market.
     */
    public void performTradeAccordingToShoppingList(StockListing shoppingCart, StockListing shoppingList, PriceList priceList, MoneyBox customersMoney) {
        for (int i = 0; i < StockKeepUnit.values().length; ++i) {

            int missingAmountInCart = shoppingList.getSkuCount(i) - shoppingCart.getSkuCount(i);
            if (missingAmountInCart > 0) {
                int amountThisBoothHaveInStock = boothStock.getSkuCount(i);
                int amountTheCustomerCanAfford = priceList.getPrice(i) == 0 ? Integer.MAX_VALUE : Math.toIntExact(customersMoney.getMoney() / priceList.getPrice(i));

                int amountThisBoothWillSell = Math.min(missingAmountInCart, Math.min(amountTheCustomerCanAfford, amountThisBoothHaveInStock));

                this.boothStock.removeStockAmount(i, amountThisBoothWillSell);
                shoppingCart.addStockAmount(i, amountThisBoothWillSell);

                int tradePrice = amountThisBoothWillSell * priceList.getPrice(i);
                customersMoney.payAmount(this.boothMoney, tradePrice);
            }
        }
    }

    public ActorRef<A_ShardCompany.ShardCompanyCommand> getSeller() {
        return this.seller;
    }

    public StockListing closeBoothAndGetRemainingForSaleList() {
        this.isOpen = false;
        return this.boothStock;
    }

    public MoneyBox getBoothRevenue() {
        return this.boothMoney;
    }
}
