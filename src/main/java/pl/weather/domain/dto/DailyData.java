package pl.weather.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DailyData(
        List<String> time,
        List<String> sunrise,
        List<String> sunset,
        @JsonProperty("precipitation_sum") List<Double> precipitationSum
) {
    @JsonCreator
    public DailyData {
    }
}
