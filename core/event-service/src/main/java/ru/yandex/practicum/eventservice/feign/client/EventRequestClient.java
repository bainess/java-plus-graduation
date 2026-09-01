package ru.yandex.practicum.eventservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.explorewithme.shareddto.dto.request.ConfirmedRequestsCount;
import ru.practicum.explorewithme.shareddto.enums.ParticipationRequestStatus;

import java.util.List;

@FeignClient(name = "ewm-main-service")
public interface EventRequestClient {
    @GetMapping("/api/requests/{eventId}")
    Long countByEventIdAndStatus(@RequestParam("eventId") Long eventId, ParticipationRequestStatus status);

    @GetMapping("/api/requests/confirmedByEventIds")
    List<ConfirmedRequestsCount>  countConfirmedRequestsByEventIds(@RequestBody List<Long> eventIds);
}
