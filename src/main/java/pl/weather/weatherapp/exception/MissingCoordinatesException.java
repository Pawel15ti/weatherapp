package pl.weather.weatherapp.exception;

public class MissingCoordinatesException extends RuntimeException {
    public MissingCoordinatesException(String message) {
        super(message);
    }
}