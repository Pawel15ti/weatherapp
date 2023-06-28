package pl.weather.infrastructure.weatherreceiver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pl.weather.domain.ResultWeatherFacade;

import java.time.LocalDateTime;

@RestController
@Log4j2
@AllArgsConstructor
@Validated
public class WeatherController {

    private final ResultWeatherFacade resultWeatherFacade;

    @GetMapping("/weather-data")
    public WeatherResult getWeatherData(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude) {

        log.info("ResultWeatherFacade started...");
        WeatherResult weatherResult = resultWeatherFacade.processDataWeather(latitude, longitude, LocalDateTime.now());

        log.info("WeatherResult: " + weatherResult);

        return weatherResult;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String error = e.getName() + " should be a Double and valid range.";
        return ResponseEntity.badRequest().body(error);
    }
}