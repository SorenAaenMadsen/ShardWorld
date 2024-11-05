package com.saaenmadsen.shardworld.unittest.model;

import com.saaenmadsen.shardworld.modeltypes.MoneyBox;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyBoxTest {
    @Test
    public void testMoneyBox() {
        MoneyBox moneyBox = new MoneyBox();
        moneyBox.addMoney(100);

        assertThat("Inserted 100",
                moneyBox.getMoney(),
                equalTo(100L));

        assertThrows(IllegalArgumentException.class, () -> moneyBox.subMoney(120));

        moneyBox.subMoney(100);

        assertThat("All withdrawn again",
                moneyBox.getMoney(),
                equalTo(0L));

    }
}
