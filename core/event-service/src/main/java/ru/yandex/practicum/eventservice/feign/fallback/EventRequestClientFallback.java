package ru.yandex.practicum.eventservice.feign.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.shareddto.dto.request.ConfirmedRequestsCount;
import ru.practicum.explorewithme.shareddto.exception.EventRequestServiceNotAvailableException;
import ru.yandex.practicum.eventservice.feign.client.EventRequestClient;

import java.util.List;

@Component
public class EventRequestClientFallback implements FallbackFactory<EventRequestClient> {
    private static final Logger log = LoggerFactory.getLogger(EventRequestClientFallback.class);

    @Override
    public EventRequestClient create(Throwable cause) {
        return new EventRequestClient() {
            @Override
            public Long countByEventIdAndStatus(Long eventId) {
                log.warn("Request service is not available for countByEventIdAndStatus() for event id={}", eventId, cause);
                throw new EventRequestServiceNotAvailableException(eventId, cause);
            }

            @Override
            public List<ConfirmedRequestsCount> countConfirmedRequestsByEventIds(List<Long> eventIds) {
                log.warn("Request service is not available for countConfirmedRequestsByEventIds() for event id={}", eventIds.toString(), cause);
                throw new EventRequestServiceNotAvailableException((long) eventIds.size(), cause);
            }
        };
    }
}
