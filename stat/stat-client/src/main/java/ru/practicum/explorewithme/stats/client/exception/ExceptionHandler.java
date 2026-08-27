package ru.practicum.explorewithme.stats.client.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(StatsServerUnavailable.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleStatsServiceNotAvailable(StatsServerUnavailable e) {
        log.warn("Resource not found: {}", e.getMessage());
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
    }

}
