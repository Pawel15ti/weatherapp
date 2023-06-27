package pl.weather.weatherapp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherClient", url = "https://archive-api.open-meteo.com/v1/archive")
public interface WeatherClient {
    @GetMapping(value = "?daily=sunrise,sunset,precipitation_sum&timezone=Europe%2FWarsaw")
    WeatherResponse getWeatherData(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("start_date") String startDate,
            @RequestParam("end_date") String endDate
    );
}
