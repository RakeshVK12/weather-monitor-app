package com.example.weather_monitor.repository;

import com.example.weather_monitor.model.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherDataRepository extends MongoRepository<WeatherData,String> {

}
