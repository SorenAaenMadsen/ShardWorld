package com.saaenmadsen.shardworld.jav21cert;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.Random;
import java.util.concurrent.*;

@Ignore
public class VirtualThreadTest {
    @Test
    public void simpleTest() throws InterruptedException {
        Thread.ofVirtual().start(() -> {
            System.out.println("Hello World");
        }).join();
    }

    @Test
    public void virtualTest2() throws InterruptedException {
        Thread.Builder builder = Thread.ofVirtual().name("worker-", 0);
        Runnable task = () -> {
            System.out.println("Thread ID: " + Thread.currentThread().threadId());
        };

// name "worker-0"
        Thread t1 = builder.start(task);
        t1.join();
        System.out.println(t1.getName() + " terminated");

// name "worker-1"
        Thread t2 = builder.start(task);
        t2.join();
        System.out.println(t2.getName() + " terminated");
    }

    @Ignore
    @Test
    public void testThreadPoolExecuterAsyncstuff() {

        for (int i = 0; i < 1; ++i) {
            System.out.println("virtual threads     : " + timeRollingALotOfDiceOnExecutor(Executors.newVirtualThreadPerTaskExecutor()));
            System.out.println("1 platform threads  : " + timeRollingALotOfDiceOnExecutor(new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())));
            System.out.println("2 platform threads  : " + timeRollingALotOfDiceOnExecutor(new ThreadPoolExecutor(2, 2, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())));
            System.out.println("4 platform threads  : " + timeRollingALotOfDiceOnExecutor(new ThreadPoolExecutor(4, 4, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())));
            System.out.println("8 platform threads  : " + timeRollingALotOfDiceOnExecutor(new ThreadPoolExecutor(8, 8, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())));
            System.out.println("16 platform threads : " + timeRollingALotOfDiceOnExecutor(new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())));
            System.out.println("32 platform threads : " + timeRollingALotOfDiceOnExecutor(new ThreadPoolExecutor(32, 32, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())));
            System.out.println("64 platform threads : " + timeRollingALotOfDiceOnExecutor(new ThreadPoolExecutor(64, 64, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())));
            System.out.println("virtual threads     : " + timeRollingALotOfDiceOnExecutor(Executors.newVirtualThreadPerTaskExecutor()));
        }
    }

    @Test
    public void testVirtualExecutor() {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        System.out.println("virtual threads : " + timeRollingALotOfDiceOnExecutor(executor));

    }

    private String timeRollingALotOfDiceOnExecutor(ExecutorService executor) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        rollALotOfDiceOnExecutor(executor);
        stopWatch.stop();
        return ("" + stopWatch.getTotalTimeSeconds() + " seconds");
    }

    private void rollALotOfDiceOnExecutor(ExecutorService executor) {
        CompletableFuture<Void>[] futures = new CompletableFuture[1000000];
        for (int i = 0; i < futures.length; i++) {
            futures[i] = CompletableFuture
                    .supplyAsync(this::rollDice, executor)
                    .thenAccept(this::ignore)
            ;
        }
        for (int i = 0; i < futures.length; i++) {
            futures[i].join();
        }
    }

    private void ignore(Integer integer) {

    }

    public Integer rollDice() {
        Random dice = new Random();
        return dice.nextInt();

    }
}
