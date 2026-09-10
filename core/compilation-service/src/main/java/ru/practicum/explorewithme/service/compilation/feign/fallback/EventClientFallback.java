package ru.practicum.explorewithme.service.compilation.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.service.compilation.feign.EventClient;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
import ru.practicum.explorewithme.shareddto.dto.event.EventShortDto;
import ru.practicum.explorewithme.shareddto.exception.EventServiceNotAvailableException;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Component
public class EventClientFallback implements FallbackFactory<EventClient> {
    private static final Logger log = LoggerFactory.getLogger(EventClientFallback.class);

    @Override
    public EventClient create(Throwable cause) {
        return new EventClient() {

            @Override
            public Optional<EventFullDto> findByIdAndInitiatorId(Long eventId, Long userId) {
                log.warn("FALLBACK: Event service not available: event id={}, user id={}",eventId,userId);
                throw new EventServiceNotAvailableException(eventId, userId, cause);
            }

            @Override
            public Optional<EventFullDto> findById(Long eventId) {
                log.warn("FALLBACK: Event service not available: event id={}", eventId);
                throw new EventServiceNotAvailableException(eventId, cause);
            }

            @Override
            public Set<EventFullDto> findAllByIdFullDto(List<Long> eventIds) {
                log.warn("FALLBACK: Event service not available: event ids={}", eventIds.toString());
                return Set.of();
            }

            @Override
            public Set<EventShortDto> findAllByIdShortDto(List<Long> eventIds) {
                return Set.of();
            }
        };
    }
}
