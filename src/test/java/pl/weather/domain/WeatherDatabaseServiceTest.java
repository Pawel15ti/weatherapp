package pl.weather.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.weather.domain.dto.DailyData;
import pl.weather.domain.dto.DailyUnits;
import pl.weather.domain.dto.ProcessedData;
import pl.weather.domain.dto.WeatherResponse;
import pl.weather.infrastructure.weatherreceiver.controller.WeatherResult;
import pl.weather.infrastructure.config.WeatherRestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WeatherDatabaseServiceTest {
    @Test
    public void shouldSaveRequestToDatabase() {
        // given
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        WeatherDatabaseService weatherDatabaseService = new WeatherDatabaseService(jdbcTemplate);
        Double latitude = 52.5;
        Double longitude = 13.400009;
        LocalDateTime requestDate = LocalDateTime.now();

        // when
        weatherDatabaseService.saveRequest(latitude, longitude, requestDate);

        // then
        verify(jdbcTemplate).update("INSERT INTO pub.weather_request(requestDateTime, latitude, longitude) VALUES (?, ?, ?)",
                requestDate, latitude, longitude);
    }

    @Test
    void shouldGetLastWeekWeather() {
        //given
        WeatherRestTemplate restTemplate = Mockito.mock(WeatherRestTemplate.class);
        WeatherService service = new WeatherService(restTemplate);
        Double latitude = 52.5;
        Double longitude = 13.400009;
        LocalDateTime requestDate = LocalDateTime.of(2023, 6, 28, 0, 0);

        WeatherResponse mockResponse = createMockWeatherResponse();
        Mockito.when(restTemplate.getWeather(eq(latitude), eq(longitude), eq("2023-06-19"), eq("2023-06-25")))
                .thenReturn(mockResponse);

        //when
        WeatherResult result = service.getLastWeekWeather(latitude, longitude, requestDate);

        //then
        verify(restTemplate).getWeather(latitude, longitude, "2023-06-19", "2023-06-25");
        List<ProcessedData> processedData = result.processedDataList();

        assertEquals(7, processedData.size());

        assertEquals("2023-06-19", processedData.get(0).date());
        assertEquals(1.1666666666666667, processedData.get(0).averagePrecipitation());
        assertEquals("2023-06-19T04:40", processedData.get(0).sunriseTime());
        assertEquals("2023-06-19T21:34", processedData.get(0).sunsetTime());

    }

    private WeatherResponse createMockWeatherResponse() {
        List<String> timeData = List.of(
                "2023-06-19",
                "2023-06-20",
                "2023-06-21",
                "2023-06-22",
                "2023-06-23",
                "2023-06-24",
                "2023-06-25"
        );

        List<String> sunriseData = List.of(
                "2023-06-19T04:40",
                "2023-06-20T04:40",
                "2023-06-21T04:40",
                "2023-06-22T04:41",
                "2023-06-23T04:41",
                "2023-06-24T04:41",
                "2023-06-25T04:42"
        );

        List<String> sunsetData = List.of(
                "2023-06-19T21:34",
                "2023-06-20T21:35",
                "2023-06-21T21:35",
                "2023-06-22T21:35",
                "2023-06-23T21:35",
                "2023-06-24T21:35",
                "2023-06-25T21:35"
        );

        List<Double> precipitationSumData = new ArrayList<>(Arrays.asList(1.0, 1.1, 1.4, null, null, null, null));


        return new WeatherResponse(
                52.5,
                13.400009,
                0.3050565719604492,
                7200,
                "Europe/Warsaw",
                "CEST",
                43,
                new DailyUnits("iso8601", "iso8601", "iso8601", "mm"),
                new DailyData(timeData, sunriseData, sunsetData, precipitationSumData)
        );
    }
}