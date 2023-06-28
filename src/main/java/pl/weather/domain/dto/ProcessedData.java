package pl.weather.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProcessedData(String date, Double averagePrecipitation, String sunriseTime, String sunsetTime) {
}
