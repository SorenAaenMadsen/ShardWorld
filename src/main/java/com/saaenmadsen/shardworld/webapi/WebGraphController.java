package com.saaenmadsen.shardworld.webapi;

import com.saaenmadsen.shardworld.ShardWorld;
import com.saaenmadsen.shardworld.statistics.WorldEndStatsWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WebGraphController {
    private static final Logger log = LoggerFactory.getLogger(ShardWorld.class);


    @GetMapping("/data/totalworldresourses")
    public ResponseEntity<List<ShardWorld.DataPoint>> getGraphData_totalWorldResources() {
        log.info("WebGraphController GET getGraphData_totalWorldResources");
        WorldEndStatsWorld latestSummary = ShardWorld.instance.getLatestSummary();
        List<ShardWorld.DataPoint> data = latestSummary.finalWorldTotalStockMap().getAsDataPointsForWebGraph();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/data/marketpricesdayend")
    public ResponseEntity<Map<String, Object>> getGraphData_marketPriceDayEnd(
            @RequestParam(value = "country", required = true) String country,
            @RequestParam(value = "usagecategory", required = false) String usageCategory
            ) {
        log.info("WebGraphController GET getGraphData_marketPriceDayEnd. country:" + country + " usageCategory:" + usageCategory);
        WorldEndStatsWorld latestSummary = ShardWorld.instance.getLatestSummary();
        return new ResponseEntity<>(latestSummary.getPricesInAllCountriesAsDataPointsForWebGraph(country, usageCategory), HttpStatus.OK);
    }

    @GetMapping("/world/status")
    public ResponseEntity<List<ShardWorld.WorldStatusKeyValue>> getWorldControlStatus() {
        log.info("WebGraphController GET getWorldControlStatus");
        // Fetch or generate your data here
        List<ShardWorld.WorldStatusKeyValue> data = ShardWorld.instance.getWorldCompanyStatusHtmlTableInput();
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
