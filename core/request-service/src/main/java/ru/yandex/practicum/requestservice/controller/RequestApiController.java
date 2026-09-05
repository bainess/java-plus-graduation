package ru.yandex.practicum.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.shareddto.dto.request.ConfirmedRequestsCount;
import ru.yandex.practicum.requestservice.service.EventRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestApiController {
    private final EventRequestService eventRequestService;

    @GetMapping("/{eventId}")
    public Integer getNumberOfConfirmedRequests(@PathVariable ("eventId") Long eventId) {
        return eventRequestService.getConfirmedRequests(eventId);
    }

    @GetMapping("/confirmedByEventIds")
    public List<ConfirmedRequestsCount> getConfirmedRequestsByEventIds(@RequestParam List<Long> eventIds) {
        return eventRequestService.getConfirmedRequestsByEventIds(eventIds);
    }
}
