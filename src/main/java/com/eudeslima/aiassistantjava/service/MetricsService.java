package com.eudeslima.aiassistantjava.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;
import java.util.HashMap;

@Service
public class MetricsService {
    
    private final AtomicLong totalRequests = new AtomicLong(0);
    private final AtomicLong successfulRequests = new AtomicLong(0);
    private final AtomicLong failedRequests = new AtomicLong(0);
    private final LocalDateTime startTime = LocalDateTime.now();

    @Value("${ai.modelName:Desconhecido}")
    private String modelName;
    
    public void incrementTotalRequests() {
        totalRequests.incrementAndGet();
    }
    
    public void incrementSuccessfulRequests() {
        successfulRequests.incrementAndGet();
    }
    
    public void incrementFailedRequests() {
        failedRequests.incrementAndGet();
    }
    
    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        long total = totalRequests.get();
        long successful = successfulRequests.get();
        long failed = failedRequests.get();
        
        metrics.put("totalRequests", total);
        metrics.put("successfulRequests", successful);
        metrics.put("failedRequests", failed);
        metrics.put("successRate", total > 0 ? (double) successful / total * 100 : 0);
        metrics.put("uptime", getUptime());
        metrics.put("startTime", startTime.toString());
        metrics.put("modelName", modelName);
        
        return metrics;
    }
    
    private String getUptime() {
        LocalDateTime now = LocalDateTime.now();
        java.time.Duration duration = java.time.Duration.between(startTime, now);
        
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
} 