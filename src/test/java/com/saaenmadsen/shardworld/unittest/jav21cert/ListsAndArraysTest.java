package com.saaenmadsen.shardworld.unittest.jav21cert;

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
 */
public class ListsAndArraysTest {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private record TestResult(String testId, int testSize, Duration duration) {}

    @Test
    public void testWhatStructuresToUseForEfficiency() {
        int testSize = 10000000;

        List<TestResult> results = new ArrayList<>();
        results.add(new TestResult("WithArrayListAndForLoop", testSize, testWithArrayListAndForLoop(testSize)));
        results.add(new TestResult("WithArrayListAndStreams", testSize, testWithArrayListAndStreams(testSize)));
        results.add(new TestResult("WithArrayAndStreams", testSize, testWithArrayAndStreams(testSize)));
        results.add(new TestResult("WithArrayAndForLoop", testSize, testWithArrayAndForLoop(testSize)));

        for (TestResult result : results) {
            String testIdPadded = String.format("%1$-" + 30 + "s", result.testId);
            String sizePadded = String.format("%1$" + 12 + "s", result.testSize);
            log.info("Test {} with size {} took {} milliseconds. Raw duration: {}", testIdPadded, sizePadded, result.duration.toMillis(), result.duration);
        }

    }

    private Duration testWithArrayListAndForLoop(int size){
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
        System.out.println("testWithArrayListAndForLoop " + sum);

        return Duration.between(start, end);
    }

    private Duration testWithArrayListAndStreams(int size){
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        ArrayList<Integer> list2 = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {
            list1.add(i);
            list2.add(i);
        }

        Instant start = Instant.now();

        int sum = list1.stream().mapToInt(i -> i).sum() - list2.stream().mapToInt(i -> i).sum();

        Instant end = Instant.now();
        System.out.println("WithArrayListAndStreams " + sum);

        return Duration.between(start, end);
    }


    private Duration testWithArrayAndStreams(int size){
        int[] list1 = new int[size];
        int[] list2 = new int[size];

        for (int i = 0; i < size; i++) {
            list1[i] = i;
            list2[i] = i;
        }

        Instant start = Instant.now();

        int sum = Arrays.stream(list1).sum() - Arrays.stream(list2).sum();

        Instant end = Instant.now();
        System.out.println("WithArrayListAndStreams " + sum);

        return Duration.between(start, end);
    }


    private Duration testWithArrayAndForLoop(int size){
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
        System.out.println("testWithArrayAndForLoop " + sum);

        return Duration.between(start, end);
    }
}
