package ru.yandex.practicum.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.requestservice.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.requestservice.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.requestservice.dto.ParticipationRequestDto;
import ru.yandex.practicum.requestservice.service.EventRequestService;


import java.util.List;


@SuppressWarnings("unused")
@RestController
@RequestMapping("/users/{userId}/events/{eventId}/requests")
@RequiredArgsConstructor
public class EventRequestPrivateController {

    private final EventRequestService eventRequestService;

    @GetMapping
    public List<ParticipationRequestDto> getEventRequests(@PathVariable Long userId,
                                                          @PathVariable Long eventId) {
        return eventRequestService.getEventRequests(userId, eventId);
    }

    @PatchMapping
    public EventRequestStatusUpdateResult updateEventRequests(@PathVariable Long userId,
                                                              @PathVariable Long eventId,
                                                              @RequestBody EventRequestStatusUpdateRequest request) {
        return eventRequestService.updateEventRequests(userId, eventId, request);
    }
}
