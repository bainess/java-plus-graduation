package ru.practicum.explorewithme.service.compilation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
import ru.practicum.explorewithme.shareddto.dto.event.EventShortDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@FeignClient(name = "event-service", contextId = "events")
public interface EventClient {

    @GetMapping("/api/events/{eventId}/users/{userId}")
    Optional<EventFullDto> findByIdAndInitiatorId(@PathVariable Long eventId,
                                                  @PathVariable Long userId);

    @GetMapping("/api/events/{eventId}")
    Optional<EventFullDto> findById(@PathVariable Long eventId);

    @GetMapping("/api/events/full")
    Set<EventFullDto> findAllByIdFullDto(@PathVariable List<Long> eventIds);

    @GetMapping("/api/events/short")
    Set<EventShortDto> findAllByIdShortDto(@RequestParam List<Long> eventIds);
}
