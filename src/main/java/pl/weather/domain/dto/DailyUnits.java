package pl.weather.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DailyUnits(
        String time,
        String sunrise,
        String sunset,
        String precipitation_sum
) {
    @JsonCreator
    public DailyUnits {
    }
}
