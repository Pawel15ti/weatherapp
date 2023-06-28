package pl.weather.domain;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2
@AllArgsConstructor
public class WeatherDatabaseService {

    private final JdbcTemplate jdbcTemplate;

    public void saveRequest(Double latitude, Double longitude, LocalDateTime requestDate) {
        log.info("Started save request params: " + latitude + " , " + longitude + " time: " + requestDate);
        jdbcTemplate.update("INSERT INTO pub.weather_request(requestDateTime, latitude, longitude) VALUES (?, ?, ?)",
                requestDate, latitude, longitude);
        log.info("Finished save request params");
    }
}
