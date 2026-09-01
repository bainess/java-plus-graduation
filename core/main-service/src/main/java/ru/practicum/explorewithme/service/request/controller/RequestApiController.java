package ru.practicum.explorewithme.service.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import ru.practicum.explorewithme.service.request.service.EventRequestService;
import ru.practicum.explorewithme.shareddto.dto.request.ConfirmedRequestsCount;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestApiController {
    private final EventRequestService eventRequestService;

    @GetMapping("/{eventId}")
    public Integer getNumberOfConfirmedRequests(@RequestParam ("eventId") Long eventId) {
        return eventRequestService.getConfirmedRequests(eventId);
    }

    @GetMapping("/confirmedByEventIds")
    public List<ConfirmedRequestsCount> getConfirmedRequestsByEventIds(@RequestBody List<Long> eventIds) {
        return eventRequestService.getConfirmedRequestsByEventIds(eventIds);
    }
}
