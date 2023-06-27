package pl.weather.weatherapp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;


    @GetMapping("/test")
    public int test() {
        return 1;
    }

    @GetMapping("/weather-data")
    public WeatherResult getWeatherData(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude) {


        WeatherResult lastWeekWeather = weatherService.getLastWeekWeather(latitude, longitude);
        return lastWeekWeather;
    }
}