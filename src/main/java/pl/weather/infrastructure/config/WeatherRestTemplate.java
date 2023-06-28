package pl.weather.infrastructure.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.weather.domain.dto.WeatherResponse;

@Service
@Log4j2
public class WeatherRestTemplate {
    public static final String SUNRISE_SUNSET_PRECIPITATION_SUM = "sunrise,sunset,precipitation_sum";
    public static final String EUROPE_WARSAW = "Europe/Warsaw";
    private final String weatherApiUrl;
    private final RestTemplate restTemplate;

    public WeatherRestTemplate(@Value("${weather.api.url}") String weatherApiUrl, RestTemplate restTemplate) {
        this.weatherApiUrl = weatherApiUrl;
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(double latitude, double longitude, String startDate, String endDate) {
        log.info("Fetching weather");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(weatherApiUrl)
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("start_date", startDate)
                .queryParam("end_date", endDate)
                .queryParam("daily", SUNRISE_SUNSET_PRECIPITATION_SUM)
                .queryParam("timezone", EUROPE_WARSAW);

        return restTemplate.getForObject(builder.toUriString(), WeatherResponse.class);
    }
}
