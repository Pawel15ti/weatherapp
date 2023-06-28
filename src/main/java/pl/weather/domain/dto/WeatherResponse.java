package pl.weather.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(

        double latitude,
        double longitude,
        double generationtime_ms,
        int utc_offset_seconds,
        String timezone,
        String timezone_abbreviation,
        int elevation,
        DailyUnits daily_units,
        DailyData daily
) {
}
