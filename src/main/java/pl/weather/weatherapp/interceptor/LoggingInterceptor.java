package pl.weather.weatherapp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import pl.weather.weatherapp.exception.InvalidCoordinatesException;

import java.time.LocalDateTime;

public class LoggingInterceptor implements HandlerInterceptor {

    public static final String REGEX = "-?\\d+(\\.\\d+)?";
    private final JdbcTemplate jdbcTemplate;

    public LoggingInterceptor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        // Sprawdź, czy oba parametry są obecne w żądaniu
        if (latitude != null && longitude != null) {
            double latitudeValue = 0d;
            double longitudeValue = 0d;

            try {
                latitudeValue = parseLatitude(latitude);
                longitudeValue = parseLongitude(longitude);

                saveRequestToDatabase(LocalDateTime.now(), latitudeValue, longitudeValue);
            } catch (Exception e) {
                saveErrorToDatabase(LocalDateTime.now(), latitude, longitude);
                throw e;
            }
        }

        return true;
    }

    private double parseLatitude(String latitude) {
        if (!latitude.matches(REGEX)) {
            throw new InvalidCoordinatesException("Invalid format for latitude.");
        }
        return Double.parseDouble(latitude);
    }

    private double parseLongitude(String longitude) {
        if (!longitude.matches(REGEX)) {
            throw new InvalidCoordinatesException("Invalid format for longitude.");
        }
        return Double.parseDouble(longitude);
    }

    private void saveRequestToDatabase(LocalDateTime requestDateTime, double latitude, double longitude) {
        jdbcTemplate.update("INSERT INTO pub.weather_request(requestDateTime, latitude, longitude) VALUES (?, ?, ?)",
                requestDateTime, latitude, longitude);
    }

    private void saveErrorToDatabase(LocalDateTime requestDateTime, String latitude, String longitude) {
        jdbcTemplate.update("INSERT INTO pub.weather_request(requestDateTime, latitude, longitude) VALUES (?, ?, ?)",
                requestDateTime, latitude, longitude);
    }
}