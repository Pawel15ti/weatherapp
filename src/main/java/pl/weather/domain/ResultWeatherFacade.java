package pl.weather.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.weather.infrastructure.weatherreceiver.controller.WeatherResult;

import java.time.LocalDateTime;

@Log4j2
@AllArgsConstructor
@Service
public class ResultWeatherFacade {

    private final WeatherService weatherService;
    private final WeatherDatabaseService weatherDatabaseService;

    public WeatherResult processDataWeather(Double latitude, Double longitude, LocalDateTime requestDate) {
        log.info("Started processDataWeather: " + latitude + " " + longitude);

        weatherDatabaseService.saveRequest(latitude, longitude, requestDate);
        log.info("Finished processDataWeather");
        return weatherService.getLastWeekWeather(latitude, longitude, requestDate);
    }
}
