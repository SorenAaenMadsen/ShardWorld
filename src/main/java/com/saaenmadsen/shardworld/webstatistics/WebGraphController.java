package com.saaenmadsen.shardworld.webstatistics;

import com.saaenmadsen.shardworld.Main;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/data")
public class WebGraphController {

    public record DataPoint(String label, int data){};

    @GetMapping("/graph")
    public ResponseEntity<List<DataPoint>> getGraphData() {
        // Fetch or generate your data here
        List<DataPoint> data = fetchData();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    private List<DataPoint> fetchData() {
        // Example data or logic to fetch from a database
        return Main.summary.finalWorldTotalStockMap().stream().map(stock->new DataPoint(stock.getKey(), stock.getValue())).collect(Collectors.toUnmodifiableList());
//        return List.of(new DataPoint("Label1", 100), new DataPoint("Label2", 200));
    }
}
