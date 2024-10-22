package com.saaenmadsen.shardworld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.typed.Behavior;
import akka.persistence.typed.PersistenceId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import static com.sun.org.apache.xml.internal.serializer.Method.TEXT;

@SpringBootApplication
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        log.info("Starting up Main");

        ActorSystem system = ActorSystem.create("shardworld");


//        ActorRef readingActorRef = system.actorOf(ReadingActor.props("Lorum ipsum\nHan Solo"), "readingActor");
//        readingActorRef.tell(new ReadingActor.ReadLines(), ActorRef.noSender());

//        ActorRef persistentActor = system.actorOf(MyPersistentBehavior);
//        Behavior<MyPersistentBehavior.Command> persistentActor = MyPersistentBehavior.create(new PersistenceId("1234"));
//        persistentActor.


//        CompletableFuture<Object> future = ask(wordCounterActorRef,
//                new WordCounterActor.CountWords(line), 1000).toCompletableFuture();


        log.info("Done with Main");
    }


}