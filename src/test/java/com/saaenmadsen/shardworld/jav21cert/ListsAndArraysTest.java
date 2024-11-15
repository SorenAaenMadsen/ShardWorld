package com.saaenmadsen.shardworld.jav21cert;

import com.saaenmadsen.shardworld.Main;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * To check the difference in performance of structures and arrays, I make two lists of numbers, and
 * sum up multiplied elements, like L1E1-L2E1 + L1E2-L2E2 etc.
 * Timings are pretty reliable. The heap impact estimation does not work at all. That should be little surprise, in a GC
 * Timings are pretty reliable. The heap impact estimation does not work at all. That should be little surprise, in a GC
 * world, but still, annoying. :)
 */
public class ListsAndArraysTest {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private record TestResult(String testId, int testSize, Duration duration, HeapCheck beforeRun, HeapCheck afterRun) {}

    private record HeapCheck(long heapSize, long maxHeapSize, long freeMemory) {
        public static HeapCheck checkHeap() {
            // Get the Java runtime
            Runtime runtime = Runtime.getRuntime();

            // Calculate current heap size
            long heapSize = runtime.totalMemory(); // Total memory currently in use by JVM
            long maxHeapSize = runtime.maxMemory(); // Maximum memory JVM can use
            long freeMemory = runtime.freeMemory(); // Free memory in the currently allocated heap

//            System.out.printf("   Current Heap Size: %.2f MB%n", heapSize / 1024.0 / 1024.0);
//            System.out.printf("   Maximum Heap Size: %.2f MB%n", maxHeapSize / 1024.0 / 1024.0);
//            System.out.printf("   Free Memory in Heap: %.2f MB%n", freeMemory / 1024.0 / 1024.0);

            return new HeapCheck(heapSize, maxHeapSize, freeMemory);
        }
    }


private void tryToFreeMemory(){
    System.gc();
    try {
        Thread.sleep(1500);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    System.gc();
    try {
        Thread.sleep(1500);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
}




    @Test
    public void testWhatStructuresToUseForEfficiency() {
        int testSize = 20000000;


        List<TestResult> results = new ArrayList<>();
        results.add(testWithArrayListAndForLoop(testSize));
        results.add(testWithArrayListAndStreams(testSize));
        results.add(testWithArrayAndStreams(testSize));
        results.add(testWithArrayAndForLoop(testSize));

        for (TestResult result : results) {
            String testIdPadded = String.format("%1$-" + 30 + "s", result.testId);
            String sizePadded = String.format("%1$" + 12 + "s", result.testSize);
            //String freeMemReduction = String.format("%,d", result.afterRun.freeMemory - result.beforeRun.freeMemory);
            log.info("Test {} with size {} took {} milliseconds. Raw duration: {}.", testIdPadded, sizePadded, result.duration.toMillis(), result.duration);
        }

    }

    private TestResult testWithArrayListAndForLoop(int size){
        String thisMethodName = new Object() {}.getClass().getEnclosingMethod().getName();
        HeapCheck before = HeapCheck.checkHeap();
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        ArrayList<Integer> list2 = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {
            list1.add(i);
            list2.add(i);
        }

        Instant start = Instant.now();

        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += list1.get(i);
            sum -= list2.get(i);
        }
        Instant end = Instant.now();
        System.out.println(thisMethodName+" " + sum);
        HeapCheck after = HeapCheck.checkHeap();
        return new TestResult(thisMethodName, size, Duration.between(start, end), before, after );

    }

    private TestResult testWithArrayListAndStreams(int size){
        String thisMethodName = new Object() {}.getClass().getEnclosingMethod().getName();
        HeapCheck before = HeapCheck.checkHeap();
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        ArrayList<Integer> list2 = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {
            list1.add(i);
            list2.add(i);
        }

        Instant start = Instant.now();

        int sum = list1.stream().mapToInt(i -> i).sum() - list2.stream().mapToInt(i -> i).sum();

        Instant end = Instant.now();
        System.out.println(thisMethodName+" " + sum);
        HeapCheck after = HeapCheck.checkHeap();

        return new TestResult(thisMethodName, size, Duration.between(start, end), before, after );
    }


    private TestResult testWithArrayAndStreams(int size){
        String thisMethodName = new Object() {}.getClass().getEnclosingMethod().getName();
        HeapCheck before = HeapCheck.checkHeap();
        int[] list1 = new int[size];
        int[] list2 = new int[size];

        for (int i = 0; i < size; i++) {
            list1[i] = i;
            list2[i] = i;
        }

        Instant start = Instant.now();

        int sum = Arrays.stream(list1).sum() - Arrays.stream(list2).sum();

        Instant end = Instant.now();
        System.out.println(thisMethodName+" " + sum);
        HeapCheck after = HeapCheck.checkHeap();

        return new TestResult(thisMethodName, size, Duration.between(start, end), before, after );
    }


    private TestResult testWithArrayAndForLoop(int size){
        String thisMethodName = new Object() {}.getClass().getEnclosingMethod().getName();
        HeapCheck before = HeapCheck.checkHeap();
        int[] list1 = new int[size];
        int[] list2 = new int[size];

        for (int i = 0; i < size; i++) {
            list1[i] = i;
            list2[i] = i;
        }

        Instant start = Instant.now();

        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += list1[i];
            sum -= list2[i];
        }


        Instant end = Instant.now();
        System.out.println(thisMethodName+" " + sum);
        HeapCheck after = HeapCheck.checkHeap();

        return new TestResult(thisMethodName, size, Duration.between(start, end), before, after );
    }
}
