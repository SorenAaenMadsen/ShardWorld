package com.saaenmadsen.shardworld.integrationtest.util;


import akka.actor.typed.ActorSystem;
import com.saaenmadsen.shardworld.actors.shardcountry.CountryMainActor;

public class CountryLevelIntegrationTestUtil {
    private ActorSystem system;
    public CountryLevelIntegrationTestUtil() {
        system = ActorSystem.create(CountryMainActor.create(), "MyCountryActor");
    }
}
