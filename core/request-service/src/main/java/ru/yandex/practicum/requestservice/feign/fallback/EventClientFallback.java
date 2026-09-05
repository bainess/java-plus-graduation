package ru.yandex.practicum.requestservice.feign.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
import ru.practicum.explorewithme.shareddto.exception.EventServiceNotAvailableException;
import ru.yandex.practicum.requestservice.feign.EventClient;

import java.util.Optional;

@Component
public class EventClientFallback implements FallbackFactory<EventClient> {
    private static final Logger log = LoggerFactory.getLogger(EventClientFallback.class);

    @Override
    public EventClient create(Throwable cause) {
        return new EventClient() {
            @Override
            public Optional<EventFullDto> findByIdAndInitiatorId(Long eventId, Long userId) {
                log.warn("Event service (by request service) is not available" +
                        " for findByIdAndInitiatorId for event id={} user id={}", eventId, userId, cause);
                throw new EventServiceNotAvailableException(eventId, cause);
            }

            @Override
            public Optional<EventFullDto> findEventById(Long eventId) {
                log.warn("Event service is not available for findEventById event is={}", eventId, cause);
                throw new EventServiceNotAvailableException(eventId, cause);
            }
        };
    }
}
