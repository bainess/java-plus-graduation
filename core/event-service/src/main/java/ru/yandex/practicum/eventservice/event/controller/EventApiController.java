package ru.yandex.practicum.eventservice.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
import ru.practicum.explorewithme.shareddto.dto.event.EventShortDto;
import ru.yandex.practicum.eventservice.event.service.EventService;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventApiController {
    private final EventService eventService;

    @GetMapping("/{eventId}/users/{userId}")
    public Optional<EventFullDto> findByIdAndInitiatorId(@PathVariable Long eventId,
                                                  @PathVariable Long userId) {
        return Optional.of(eventService.getEventFullDto(eventId, userId));
    }

    @GetMapping("/{eventId}")
    public Optional<EventFullDto> findById(@PathVariable Long eventId) {
        return Optional.of(eventService.getEventFullDto(eventId));
    }

    @GetMapping("/full")
    public Set<EventFullDto> findAllByIdFullDto(@PathVariable Set<Long>eventIds) {
        return eventService.findAllById(eventIds);
    }

    @GetMapping("/short")
    Set<EventShortDto> findAllByIdShortDto(@RequestParam Set<Long> eventIds) {
        return eventService.findAllByIdShort(eventIds);
    }

}
