package pl.weather.weatherapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MissingCoordinatesException.class)
    public ResponseEntity<Object> handleMissingCoordinatesException(MissingCoordinatesException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCoordinatesException.class)
    public ResponseEntity<Object> handleInvalidCoordinatesException(InvalidCoordinatesException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
