package ru.practicum.explorewithme.service.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.explorewithme.shareddto.dto.event.EventShortDto;

import java.util.List;

@FeignClient(name = "event-service", contextId = "event-request")
public interface EventClient {
    @GetMapping("/users/{userId}/events")
    List<EventShortDto> getEventsByUser (@PathVariable Long userId);
}
