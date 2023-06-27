package pl.weather.weatherapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private double latitude;
    private double longitude;
    private double generationtime_ms;
    private int utc_offset_seconds;
    private String timezone;
    private String timezone_abbreviation;
    private int elevation;
    private DailyUnits daily_units;
    private DailyData daily;
}
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
 class DailyUnits {
    private String time;
    private String sunrise;
    private String sunset;
    private String precipitation_sum;
}
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
  class DailyData {
    private List<String> time;
    private List<String> sunrise;
    private List<String> sunset;
    @JsonProperty("precipitation_sum")
    private List<Double> precipitationSum;
}
