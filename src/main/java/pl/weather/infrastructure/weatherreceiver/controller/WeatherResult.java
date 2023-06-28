package pl.weather.infrastructure.weatherreceiver.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import pl.weather.domain.dto.ProcessedData;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResult(
        @Getter
        List<ProcessedData> processedDataList) {
}