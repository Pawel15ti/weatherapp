package pl.weather.domain;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.weather.domain.dto.ProcessedData;
import pl.weather.domain.dto.WeatherResponse;
import pl.weather.infrastructure.weatherreceiver.controller.WeatherResult;
import pl.weather.infrastructure.config.WeatherRestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Log4j2
@AllArgsConstructor
public class WeatherService {

    private final WeatherRestTemplate weatherRestTemplate;

    public WeatherResult getLastWeekWeather(Double latitude, Double longitude, LocalDateTime requestDate) {
        log.info("Preparing weather params");

        LocalDate currentDate = requestDate.toLocalDate();
        LocalDate startDate = getStartDate(currentDate);
        LocalDate endDate = getEndDate(startDate);

        WeatherResponse weatherData = weatherRestTemplate.getWeather(latitude, longitude, startDate.toString(), endDate.toString());

        double averagePrecipitation = calculateAveragePrecipitation(weatherData.daily().precipitationSum());

        List<ProcessedData> processedData = processData(weatherData, averagePrecipitation);
        log.info("Responed weather params");
        return new WeatherResult(processedData);
    }

    private LocalDate getStartDate(LocalDate currentDate) {
        return currentDate.minusWeeks(1).with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
    }

    private LocalDate getEndDate(LocalDate startDate) {
        return startDate.plusDays(6);
    }

    private double calculateAveragePrecipitation(List<Double> precipitationSum) {
        return Optional.ofNullable(precipitationSum)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    private List<ProcessedData> processData(WeatherResponse weatherData, double averagePrecipitation) {
        List<String> timeData = weatherData.daily().time();
        List<String> sunriseData = weatherData.daily().sunrise();
        List<String> sunsetData = weatherData.daily().sunset();

        return IntStream.range(0, timeData.size())
                .mapToObj(index -> createProcessedData(timeData, sunriseData, sunsetData, averagePrecipitation, index))
                .collect(Collectors.toList());
    }

    private ProcessedData createProcessedData(List<String> timeData, List<String> sunriseData, List<String> sunsetData,
                                              double averagePrecipitation, int index) {
        String time = timeData.get(index);
        String sunrise = sunriseData.get(index);
        String sunset = sunsetData.get(index);

        return new ProcessedData(time, averagePrecipitation, sunrise, sunset);
    }
}