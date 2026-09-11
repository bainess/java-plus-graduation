package ru.yandex.practicum.eventservice.event.service;

import ru.practicum.explorewithme.shareddto.dto.event.*;
import ru.yandex.practicum.eventservice.event.dto.EventSearchParams;
import ru.yandex.practicum.eventservice.event.dto.EventSearchParamsAdmin;

import java.util.List;
import java.util.Set;

public interface EventService {
    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    List<EventShortDto> getEvents(Long userId, int from, int size);

    EventFullDto getEvent(Long userId, Long eventId);

    EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest request);

    List<EventShortDto> getEventsPublic(EventSearchParams params);

    EventFullDto getEventPublic(Long eventId);

    List<EventFullDto> getEventsByAdmin(EventSearchParamsAdmin params);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest request);

    List<EventFullDto> getEventsByLocation(Long locId, int from, int size);

    EventFullDto getEventFullDto(Long eventId, Long userId);

    EventFullDto getEventFullDto(Long eventId);

    Set<EventFullDto> findAllById(Set<Long> eventIds);

    Set<EventShortDto> findAllByIdShort(Set<Long> eventIds);
}
