package pl.weather.infrastructure.weatherreceiver.controller;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.weather.domain.ResultWeatherFacade;
import pl.weather.domain.dto.ProcessedData;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({WeatherController.class})
@ActiveProfiles("test")
@Log4j2
class WeatherControllerWebTest {

    @MockBean
    private ResultWeatherFacade resultWeatherFacade;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        WeatherResult weatherResult = new WeatherResult(List.of(
                new ProcessedData("2023-06-19", 1.1666666666666667d, "2023-06-19T04:40", "2023-06-19T21:34"),
                new ProcessedData("2023-06-20", 1.1666666666666667d, "2023-06-20T04:40", "2023-06-20T21:35"),
                new ProcessedData("2023-06-21", 1.1666666666666667d, "2023-06-21T04:40", "2023-06-21T21:35"),
                new ProcessedData("2023-06-22", 1.1666666666666667d, "2023-06-22T04:41", "2023-06-22T21:35"),
                new ProcessedData("2023-06-23", 1.1666666666666667d, "2023-06-23T04:41", "2023-06-23T21:35"),
                new ProcessedData("2023-06-24", 1.1666666666666667d, "2023-06-24T04:41", "2023-06-24T21:35"),
                new ProcessedData("2023-06-25", 1.1666666666666667d, "2023-06-25T04:42", "2023-06-25T21:35")
        ));
        when(resultWeatherFacade.processDataWeather(anyDouble(), anyDouble(), any(LocalDateTime.class))).thenReturn(weatherResult);
    }

    @Test
    public void shouldGetWeatherData() throws Exception {
//        when then
        mockMvc.perform(get("/weather-data")
                        .param("latitude", "52.5")
                        .param("longitude", "13.400009"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.processedDataList", hasSize(7)))
                .andExpect(jsonPath("$.processedDataList[0].date", is("2023-06-19")))
                .andExpect(jsonPath("$.processedDataList[0].averagePrecipitation", is(1.1666666666666667d)))
                .andExpect(jsonPath("$.processedDataList[0].sunriseTime", is("2023-06-19T04:40")))
                .andExpect(jsonPath("$.processedDataList[0].sunsetTime", is("2023-06-19T21:34")));
    }

    @Test
    public void shouldReturnBadRequestWhenLatitudeIsInvalid() throws Exception {
//        when then
        mockMvc.perform(get("/weather-data")
                        .param("latitude", "52.5aa")
                        .param("longitude", "13.400009"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("latitude should be a Double and valid range."));
    }
}