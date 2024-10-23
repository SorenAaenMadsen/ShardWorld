package com.saaenmadsen.shardworld.integrationtest;

import akka.actor.typed.ActorSystem;
import com.saaenmadsen.shardworld.actors.shardcountry.CountryDayStartCommand;
import com.saaenmadsen.shardworld.actors.shardcountry.CountryMainActor;
import org.junit.jupiter.api.Test;

public class CountryMarketCycleTest {
    @Test
    public void testSimpleMarketCycle() throws InterruptedException {
        ActorSystem<CountryDayStartCommand> countryActor = ActorSystem.create(CountryMainActor.create(), "MyCountryActor");
        countryActor.tell(new CountryDayStartCommand(1));

//        implicit val timeout = Timeout(FiniteDuration(1, TimeUnit.SECONDS))
//        countryActor.
//        Akka.system.actorSelection("user/" + "somename").resolveOne().onComplete {
//            case Success(actorRef) => // logic with the actorRef
//            case Failure(ex) => Logger.warn("user/" + "somename" + " does not exist")
//        }

        Thread.sleep(4000);
    }
}
