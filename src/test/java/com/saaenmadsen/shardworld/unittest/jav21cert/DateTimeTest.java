package com.saaenmadsen.shardworld.unittest.jav21cert;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DateTimeTest {


    @Test
    public void testLocalTime() {
        System.out.println("\n");

        LocalTime timeMin = LocalTime.MIN;
        System.out.println("LocalTime.MIN: " + timeMin);
        LocalTime timeMax = LocalTime.MAX;
        System.out.println("LocalTime.MAX: " + timeMax);

        LocalDate localDate = LocalDate.now();
        System.out.println("LocalDate.now: " + localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("LocalDateTime.now: " + localDateTime);

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("ZonedDateTime.now: " + zonedDateTime);

        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        System.out.println("OffsetDateTime.now: " + offsetDateTime);

        Instant instant = Instant.now();
        System.out.println("Instant.now: " + instant);

        ZonedDateTime zonedDateTimeYesterday = ZonedDateTime.now().minusDays(1);
        int compared = zonedDateTime.compareTo(zonedDateTimeYesterday);
        System.out.println("zonedDateTimeYesterday compared to today: " + compared);


        System.out.println("\n");
    }

    @Test
    public void testPeriod() {
        System.out.println("\n");
        Period period = Period.between(LocalDate.of(2015, 2, 20), LocalDate.now());
        System.out.println("Period: " + period);
        System.out.println("Period cronology: " + period.getChronology());
        System.out.println("Period getDays: " + period.getDays());
        System.out.println("Period getMonths: " + period.getMonths());
        System.out.println("Period getYears: " + period.getYears());

        Period period50Days = Period.ofDays(50);
        System.out.println("Period of 50 days: " + period50Days.getDays());
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("LocalDateTime.now: " + localDateTime);
        LocalDateTime localDateTimeIn50days = localDateTime.plus(period50Days);
        System.out.println("LocalDateTime.plus(period50Days): " + localDateTimeIn50days);

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("ZonedDateTime.now: " + zonedDateTime);
        System.out.println("zonedDateTime.minusPeriod(period50Days): " + zonedDateTime.minus(period50Days));


        System.out.println("\n");
    }

    @Test
    public void testDuration() throws InterruptedException {
        System.out.println("\n");

        Instant instantStart = Instant.now();
        Thread.sleep(100);
        Instant instantStop = Instant.now();
        Duration duration = Duration.between(instantStart, instantStop);

        System.out.println("instantStart: " + instantStart);
        System.out.println("instantStop: " + instantStop);
        System.out.println("Duration: " + duration);

        Duration duration2 = Duration.ofDays(1).minusHours(1).minusMinutes(3);
        System.out.println("duration2: " + duration2);

        System.out.println("\n");
    }


    @Test
    public void testTemporalField() {
        TemporalField temporalField = ChronoField.MINUTE_OF_HOUR;
        System.out.println("temporalField: " + temporalField);
        assertThrows(DateTimeException.class,  ()-> ChronoField.MINUTE_OF_HOUR.checkValidValue(63));

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("Getting minutes in the temporal way: " + zonedDateTime.get(temporalField));

        String s = Arrays.stream(ChronoField.values()).map(c->c +" " + zonedDateTime.getLong(c)).collect(Collectors.joining("\n"));
        System.out.println("Getting everything in the temporal way:\n" + s);
    }


}
