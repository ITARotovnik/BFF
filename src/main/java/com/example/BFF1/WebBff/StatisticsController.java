package com.example.BFF1.WebBff;

import com.example.BFF1.Log;
import com.example.BFF1.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/statistics")
public class StatisticsController {

    private final LogRepository logRepository;

    @Autowired
    public StatisticsController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping("/most-called")
    public ResponseEntity<String> getMostCalledEndpoint() {
        List<Log> allLogs = logRepository.findAll();
        Map<String, Integer> endpointCounts = new HashMap<>();

        // Count occurrences of each unique combination of method and URL
        for (Log log : allLogs) {
            String endpoint = log.getMethod() + " " + log.getUrl();
            endpointCounts.put(endpoint, endpointCounts.getOrDefault(endpoint, 0) + 1);
        }

        // Logging for debugging
        System.out.println("Endpoint counts: " + endpointCounts);

        // Find the combination with the highest count
        String mostCalledEndpoint = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : endpointCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostCalledEndpoint = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        if (mostCalledEndpoint != null) {
            return ResponseEntity.ok("Most called endpoint: " + mostCalledEndpoint + ", called " + maxCount + " times.");
        } else {
            return ResponseEntity.ok("No logs found.");
        }
    }

    @GetMapping("/last-called")
    public ResponseEntity<String> getLastCalledEndpoint() {
        List<Log> allLogs = logRepository.findAll();

        // Find the log entry with the most recent timestamp
        Log lastLog = null;
        LocalDateTime lastTimestamp = LocalDateTime.MIN;
        for (Log log : allLogs) {
            LocalDateTime logTimestamp = log.getTimestamp();
            if (logTimestamp.isAfter(lastTimestamp)) {
                lastTimestamp = logTimestamp;
                lastLog = log;
            }
        }

        // If a log entry with the most recent timestamp is found, return its method and URL
        if (lastLog != null) {
            String lastEndpoint = lastLog.getMethod() + " " + lastLog.getUrl();
            return ResponseEntity.ok("Last called endpoint: " + lastEndpoint + ", at timestamp: " + lastLog.getTimestamp());
        } else {
            return ResponseEntity.ok("No logs found.");
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getEndpointStatistics() {
        List<Log> allLogs = logRepository.findAll();
        Map<String, Integer> endpointCounts = new HashMap<>();

        for (Log log : allLogs) {
            String endpoint = log.getMethod() + " " + log.getUrl();
            endpointCounts.put(endpoint, endpointCounts.getOrDefault(endpoint, 0) + 1);
        }

        // Find the log entry
        Log lastLog = null;
        LocalDateTime lastTimestamp = LocalDateTime.MIN;
        for (Log log : allLogs) {
            LocalDateTime logTimestamp = log.getTimestamp();
            if (logTimestamp.isAfter(lastTimestamp)) {
                lastTimestamp = logTimestamp;
                lastLog = log;
            }
        }

        String lastEndpoint = lastLog != null ? lastLog.getMethod() + " " + lastLog.getUrl() : "No logs found";

        Map<String, Integer> response = new HashMap<>();
        response.put("lastCalledEndpoint", 1); // Only one last called endpoint
        response.putAll(endpointCounts); // Add counts for each endpoint

        return ResponseEntity.ok(response);
    }


}
