package com.example.weather_monitor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "daily_summary")
public class DailySummary {
    @Id
    private String id;
    private String city;
    private double avgTemp;
    private double maxTemp;
    private double minTemp;
    private String dominantCondition;
    private LocalDate date;

    // Constructors, Getters, and Setters
 // Default constructor
    public DailySummary() {
    }

    // Parameterized constructor
    public DailySummary(String city, double avgTemp, double maxTemp, double minTemp, String dominantCondition, LocalDate date) {
        this.city = city;
        this.avgTemp = avgTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.dominantCondition = dominantCondition;
        this.date = date;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public String getDominantCondition() {
        return dominantCondition;
    }

    public void setDominantCondition(String dominantCondition) {
        this.dominantCondition = dominantCondition;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DailySummary{" +
                "id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", avgTemp=" + avgTemp +
                ", maxTemp=" + maxTemp +
                ", minTemp=" + minTemp +
                ", dominantCondition='" + dominantCondition + '\'' +
                ", date=" + date +
                '}';
    }
}
