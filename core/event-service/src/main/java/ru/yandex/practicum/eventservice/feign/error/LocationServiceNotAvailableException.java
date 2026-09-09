package ru.yandex.practicum.eventservice.feign.error;

public class LocationServiceNotAvailableException extends RuntimeException {
    public LocationServiceNotAvailableException(Long num, String message) {
        super(message);
    }
    public LocationServiceNotAvailableException(Long aLong, Throwable cause) {

    }
}
