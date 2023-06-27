package pl.weather.weatherapp.interceptor;

import org.junit.jupiter.api.Test;
import org.mockito.verification.VerificationMode;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import pl.weather.weatherapp.exception.InvalidCoordinatesException;
import pl.weather.weatherapp.exception.MissingCoordinatesException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LoggingInterceptorTest {


    @Test
    public void testPreHandle_ValidCoordinates_SuccessfulSave() throws Exception {
        // Arrange
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        LoggingInterceptor interceptor = new LoggingInterceptor(jdbcTemplate);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("latitude", "52.2297");
        request.addParameter("longitude", "21.0122");
        MockHttpServletResponse response = new MockHttpServletResponse();
        Object handler = new Object();

        // Act
        interceptor.preHandle(request, response, handler);

        // Assert
        verify(jdbcTemplate, times(1)).update(
                eq("INSERT INTO pub.weather_request(requestDateTime, latitude, longitude) VALUES (?, ?, ?)"),
                any(LocalDateTime.class), eq(52.2297), eq(21.0122));
    }

    private JdbcOperations customVerify(JdbcTemplate jdbcTemplate, VerificationMode times) {
        JdbcOperations o = null;
        return o;
    }

    @Test
    public void testPreHandle_MissingCoordinates_ThrowsException() {
        // Arrange
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        LoggingInterceptor interceptor = new LoggingInterceptor(jdbcTemplate);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Object handler = new Object();

        // Act & Assert
        assertThrows(MissingCoordinatesException.class, () -> interceptor.preHandle(request, response, handler));

        // Verify that saveRequestToDatabase and saveErrorToDatabase are not called
        customVerify(jdbcTemplate, never()).update(anyString(), any(), any(), any());
    }

    @Test
    public void testPreHandle_InvalidLatitude_ThrowsException() {
        // Arrange
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        LoggingInterceptor interceptor = new LoggingInterceptor(jdbcTemplate);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("latitude", "abc");
        request.addParameter("longitude", "21.0122");
        MockHttpServletResponse response = new MockHttpServletResponse();
        Object handler = new Object();

        // Act & Assert
        assertThrows(InvalidCoordinatesException.class, () -> interceptor.preHandle(request, response, handler));

        // Verify


    }
}