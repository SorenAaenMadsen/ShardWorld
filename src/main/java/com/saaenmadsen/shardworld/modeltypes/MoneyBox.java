package com.saaenmadsen.shardworld.modeltypes;

public class MoneyBox {
    long money;

    public long getMoney() {
        return money;
    }

    public void addMoney(long moneyToAdd) {
        this.money += moneyToAdd;
    }

    public void subMoney(long moneyToSub) {
        if(this.money>=moneyToSub){
        this.money -= moneyToSub;
        } else {
            throw new IllegalArgumentException(String.format("Cannot withdraw {} from wallet of only {}",  moneyToSub, money));
        }
    }

    public MoneyBox duplicate(){
        MoneyBox copy = new MoneyBox();
        copy.addMoney(money);
        return copy;
    }
}
