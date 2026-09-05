package ru.practicum.explorewithme.service.compilation.feign.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

public class EventClientFallback implements FallbackFactory<EventClientFallback> {
    private static final Logger log = LoggerFactory.getLogger(EventClientFallback.class);

    @Override
    public EventClientFallback create(Throwable cause) {
        return new EventClientFallback() {

        };
    }
}
