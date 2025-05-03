package com.example.weather_monitor.controller;

import com.example.weather_monitor.model.DailySummary;
import com.example.weather_monitor.model.WeatherData;
import com.example.weather_monitor.repository.DailySummaryRepository;
import com.example.weather_monitor.repository.WeatherDataRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherDataRepository weatherDataRepository;
    private final DailySummaryRepository dailySummaryRepository;

    public WeatherController(WeatherDataRepository weatherDataRepository, DailySummaryRepository dailySummaryRepository) {
        this.weatherDataRepository = weatherDataRepository;
        this.dailySummaryRepository = dailySummaryRepository;
    }

    @GetMapping("/current")
    public List<WeatherData> getCurrentWeatherData() {
        return weatherDataRepository.findAll();
    }

    @GetMapping("/summary")
    public List<DailySummary> getDailySummaries() {
        return dailySummaryRepository.findAll();
    }
}
