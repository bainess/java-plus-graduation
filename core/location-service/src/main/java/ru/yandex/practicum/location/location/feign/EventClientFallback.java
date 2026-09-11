package ru.yandex.practicum.location.location.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class EventClientFallback implements FallbackFactory<EventClient> {
    @Override
    public EventClient create(Throwable cause) {
        return new EventClient() {
            @Override
            public List<EventFullDto> getEventsByLocation(Long searchLocId, int from, int size) {
                log.warn("FALLBACK: Event service is unavailable for LocationClient. searchLockId={}", searchLocId, cause);
                return List.of();
            }
        };
    }

}
