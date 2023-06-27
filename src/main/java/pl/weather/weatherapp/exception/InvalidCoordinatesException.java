package pl.weather.weatherapp.exception;

public class InvalidCoordinatesException extends RuntimeException {
    public InvalidCoordinatesException(String message) {
        super(message);
    }
}