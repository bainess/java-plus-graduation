package ru.yandex.practicum.location.location.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;

import java.util.List;

@FeignClient(name = "event-service", fallbackFactory = EventClientFallback.class)
public interface EventClient {
    @GetMapping("api/{searchLocId}/events")
    List<EventFullDto> getEventsByLocation(@PathVariable(name = "searchLocId") Long searchLocId,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size);

}
