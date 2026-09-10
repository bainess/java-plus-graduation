package ru.practicum.userservice.feingn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.explorewithme.shareddto.dto.event.EventShortDto;
import ru.practicum.userservice.feingn.fallback.EventClientFallback;

import java.util.List;

@FeignClient(name = "event-service", contextId = "event-request",
fallbackFactory = EventClientFallback.class)
public interface EventClient {
    @GetMapping("/users/{userId}/events")
    List<EventShortDto> getEventsByUser(@PathVariable Long userId);
}
