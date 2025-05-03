package com.example.weather_monitor.service;

import com.example.weather_monitor.model.WeatherData;
import com.example.weather_monitor.repository.DailySummaryRepository;
import com.example.weather_monitor.repository.WeatherDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.weather_monitor.model.DailySummary; 

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherService {
    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;

    private final List<String> cities = List.of("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad");
    private final WeatherDataRepository weatherDataRepository;
    private final DailySummaryRepository dailySummaryRepository; // Add this line
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeatherService(WeatherDataRepository weatherDataRepository, DailySummaryRepository dailySummaryRepository) {
        this.weatherDataRepository = weatherDataRepository;
        this.dailySummaryRepository = dailySummaryRepository; // Initialize DailySummaryRepository
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Scheduled(fixedRateString = "${weather.refresh.interval}")
    public void fetchWeatherData() {
        for (String city : cities) {
            String url = apiUrl.replace("{city}", city).replace("{apiKey}", apiKey);
            System.out.println("Calling API for city: " + city);
            System.out.println("API URL: " + url);

            try {
                // Fetch the raw response
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                String jsonResponse = responseEntity.getBody();
                System.out.println("Raw JSON Response: " + jsonResponse); // Log the response

                if (jsonResponse != null) {
                    // Parse JSON response to WeatherData object
                    WeatherData weatherData = objectMapper.readValue(jsonResponse, WeatherData.class);
                    weatherData.setTimestamp(LocalDateTime.now()); // Set the current timestamp

                    // Save the weather data to the repository
                    weatherDataRepository.save(weatherData);
                    System.out.println("Saved weather data for: " + city + weatherData);

                    // Create a DailySummary from WeatherData
                    createDailySummary(city, weatherData);
                } else {
                    System.out.println("No data received for: " + city);
                }
            } catch (Exception e) {
                System.err.println("Error fetching weather data for city: " + city);
                e.printStackTrace();
            }
        }
    }

    private void createDailySummary(String city, WeatherData weatherData) {
        DailySummary dailySummary = new DailySummary();
        dailySummary.setCity(city);
        dailySummary.setAvgTemp(weatherData.getMain().getTemperature()); // Access temperature from Main
        dailySummary.setMaxTemp(weatherData.getMain().getTempMax()); // Use the max temperature
        dailySummary.setMinTemp(weatherData.getMain().getTempMin()); // Use the min temperature
        dailySummary.setDominantCondition(determineDominantCondition(weatherData)); // Set the dominant condition
        dailySummary.setDate(LocalDate.now());

        // Save the DailySummary to the repository
        dailySummaryRepository.save(dailySummary);
        System.out.println("Saved daily summary for: " + city + " " + dailySummary);
    }
    
    public String determineDominantCondition(WeatherData weatherData) {
        double temperature = weatherData.getMain().getTemperature();
        double humidity = weatherData.getMain().getHumidity();

        if (humidity > 70) {
            return "Humid";
        } else if (temperature > 30) { 
            return "Hot";
        } else if (temperature < 0) {
            return "Cold";
        } else {
            return "Normal";
        }
    }
}
