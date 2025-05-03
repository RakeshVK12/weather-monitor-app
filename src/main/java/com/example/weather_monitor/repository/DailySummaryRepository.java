package com.example.weather_monitor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.weather_monitor.model.DailySummary;

public interface DailySummaryRepository extends MongoRepository<DailySummary, String> {

}
