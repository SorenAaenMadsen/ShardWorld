package com.saaenmadsen.shardworld.countryLevelIntegrationTests.util;


import akka.actor.typed.ActorSystem;
import com.saaenmadsen.shardworld.shardcountry.CountryMainActor;

public class CountryLevelIntegrationTestUtil {
    private ActorSystem system;
    public CountryLevelIntegrationTestUtil() {
        system = ActorSystem.create(CountryMainActor.create(), "MyCountryActor");
    }
}
