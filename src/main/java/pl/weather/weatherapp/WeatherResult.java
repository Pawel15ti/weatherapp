package pl.weather.weatherapp;

import java.util.List;

public record WeatherResult(List<ProcessedData> processedDataList) {
}