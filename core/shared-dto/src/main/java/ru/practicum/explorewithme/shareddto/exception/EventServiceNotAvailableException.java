package ru.practicum.explorewithme.shareddto.exception;

public class EventServiceNotAvailableException extends RuntimeException {
    public EventServiceNotAvailableException(Long num, String message) {
        super(message);
    }
    public EventServiceNotAvailableException(Long aLong, Throwable cause) {

    }
}
