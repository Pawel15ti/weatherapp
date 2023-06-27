package pl.weather.weatherapp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherResult getLastWeekWeather(double latitude, double longitude) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusWeeks(1).with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
        LocalDate endDate = startDate.plusDays(6);
     WeatherResponse weatherData = weatherClient.getWeatherData(latitude, longitude, startDate.toString(), endDate.toString());

        double averagePrecipitation = calculateAverage(weatherData.getDaily().getPrecipitationSum());

        List<ProcessedData> processedData = IntStream.range(0, weatherData.getDaily().getTime().size())
                .mapToObj(i -> new ProcessedData(weatherData.getDaily().getTime().get(i),
                        averagePrecipitation,
                        weatherData.getDaily().getSunrise().get(i),
                        weatherData.getDaily().getSunset().get(i)))
                .collect(Collectors.toList());
        return new WeatherResult(processedData);
    }

    private static double calculateAverage(List<Double> precipitationSum) {
        System.out.println(precipitationSum);
        if (precipitationSum == null || precipitationSum.isEmpty()) {
            return 0.0;
        }
        return precipitationSum.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);    }

    private WeatherResponse getWeather() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://archive-api.open-meteo.com/v1/archive?latitude=52.52&longitude=13.41&start_date=2023-06-02&end_date=2023-06-08&daily=sunrise,sunset,precipitation_sum&timezone=Europe/Warsaw";
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}