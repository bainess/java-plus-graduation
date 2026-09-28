package ru.yandex.practicum.eventservice.feign.client;

import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.explorewithme.shareddto.dto.request.ConfirmedRequestsCount;
import ru.yandex.practicum.eventservice.feign.fallback.EventRequestClientFallback;

import java.util.List;

@FeignClient(name = "request-service", contextId = "event-request",
        fallbackFactory = EventRequestClientFallback.class)
public interface EventRequestClient {
    @GetMapping("/api/requests/{eventId}")
    Long countByEventIdAndStatus(@PathVariable("eventId") Long eventId);

    @GetMapping("/api/requests/confirmedByEventIds")
    List<ConfirmedRequestsCount> countConfirmedRequestsByEventIds(@RequestParam List<Long> eventIds);

    @GetMapping("/api/requests/{eventId}/{userId}")
    Boolean isRegistered(@PathVariable("eventId") long eventId,
                         @PathVariable("userId") long userId);
}
