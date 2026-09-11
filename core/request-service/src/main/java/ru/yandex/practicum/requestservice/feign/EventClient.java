package ru.yandex.practicum.requestservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
import ru.yandex.practicum.requestservice.feign.fallback.EventClientFallback;

import java.util.Optional;

@FeignClient(name = "event-service", contextId = "events-request",
fallbackFactory = EventClientFallback.class)
public interface EventClient {

    @GetMapping("/api/events/{eventId}/users/{userId}")
    Optional<EventFullDto> findByIdAndInitiatorId(@PathVariable Long eventId,
                                                  @PathVariable Long userId);

    @GetMapping("/api/events/{eventId}")
    Optional<EventFullDto> findEventById(@PathVariable Long eventId);
}
