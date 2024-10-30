package com.saaenmadsen.shardworld.unittest.jav21cert;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwithPatternTest {

    public String switchPatternWithGuardClause(Integer decider1) {
        return switch (decider1) {
            case null -> "Found null";
            case 0,1 -> "Found 1 or 0";
            case Integer dec2asInt when dec2asInt<0 -> "negative number";
            default -> "Default";
        };
    }
    @Test
    public void testSwitchPattern() {
        assertEquals("Found null", switchPatternWithGuardClause(null));
        assertEquals("Found 1 or 0", switchPatternWithGuardClause(1));
    }

    @Test
    public void testSwitchPatternWithGuardClause() {
        assertEquals("negative number", switchPatternWithGuardClause(-10));
        assertEquals("Default", switchPatternWithGuardClause(3));

    }

    private static void invalidOrder(Object obj) {
        switch (obj) {
            case String s -> System.out.println("String: " + s);
            case CharSequence cs -> System.out.println("CS length " + cs.length());
            default -> {
                break;
            }
        }
    }

    @Test
    public void testInvalidOrder() {
     invalidOrder("String");
    }


}
