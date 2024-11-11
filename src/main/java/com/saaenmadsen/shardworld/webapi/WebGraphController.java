package com.saaenmadsen.shardworld.webapi;

import com.saaenmadsen.shardworld.ShardWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class WebGraphController {
    private static final Logger log = LoggerFactory.getLogger(ShardWorld.class);

    public record DataPoint(String label, int data) {
    }

    ;

    //    public record WorldStatus(String currentDay, String lastDayOfWorld, long aggregatedWorldStockValue){};


    ;

    @GetMapping("/data/graph")
    public ResponseEntity<List<DataPoint>> getGraphData() {
        log.info("WebGraphController GET getGraphData");
        // Fetch or generate your data here
        List<DataPoint> data = ShardWorld.instance.getLatestSummary().finalWorldTotalStockMap().stream().map(stock -> new DataPoint(stock.getKey(), stock.getValue())).collect(Collectors.toUnmodifiableList());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/world/status")
    public ResponseEntity<List<ShardWorld.WorldStatusKeyValue>> getWorldControlStatus() {
        log.info("WebGraphController GET getWorldControlStatus");
        // Fetch or generate your data here
        List<ShardWorld.WorldStatusKeyValue> data = ShardWorld.instance.getWorldStatus();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    @PostMapping("/advance-day")
    public ResponseEntity<String> advanceDay() {
        log.info("WebGraphController POST advanceDay");
        // Call your Java method that advances the world day
        ShardWorld.instance.advanceOneDay();
        return ResponseEntity.ok("Day advanced successfully!");
    }
}
