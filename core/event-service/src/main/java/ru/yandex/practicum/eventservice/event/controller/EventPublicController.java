package ru.yandex.practicum.eventservice.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
import ru.practicum.explorewithme.shareddto.dto.event.EventShortDto;
import ru.practicum.explorewithme.shareddto.exception.BadRequestException;
import ru.practicum.explorewithme.stats.dto.EndpointHitDTO;
import ru.yandex.practicum.eventservice.config.AppProperties;
import ru.yandex.practicum.eventservice.event.dto.EventSearchParams;
import ru.yandex.practicum.eventservice.event.service.EventService;
import ru.yandex.practicum.eventservice.statistics.StatisticsClient;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventPublicController {
    private final EventService eventService;
    private final AppProperties appProperties;

    private final StatisticsClient statsClient;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEvents(@RequestParam(required = false) String text,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(required = false) Boolean paid,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                         @RequestParam(required = false) Boolean onlyAvailable,
                                         @RequestParam(required = false) String sort,
                                         @RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         HttpServletRequest request
    ) {
        if (rangeEnd != null && rangeStart != null && rangeEnd.isBefore(rangeStart)) {
            throw new BadRequestException("rangeEnd должен быть позже rangeStart");
        }
        EndpointHitDTO hit = new EndpointHitDTO();
        hit.setApp("ewm-main-service");
        hit.setUri(request.getRequestURI());
        hit.setIp(request.getRemoteAddr());
        hit.setTimestamp(LocalDateTime.now());
        statsClient.saveHit(hit);

        EventSearchParams params = EventSearchParams.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .from(from)
                .size(size)
                .build();
        return eventService.getEventsPublic(params);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventByIdAndPublished(@PathVariable(name = "eventId") Long eventId,
                                                 HttpServletRequest request) {
        log.info("Запрос на получение события {}", eventId);

//        обновляет статистику по событию

        EndpointHitDTO hit = new EndpointHitDTO();
        hit.setApp(appProperties.getName());
        log.info("Название приложения {}",appProperties.getName());
        hit.setUri(request.getRequestURI());
        hit.setIp(request.getRemoteAddr());
        hit.setTimestamp(LocalDateTime.now());
        statsClient.saveHit(hit);
// получает событие

        return eventService.getEventPublic(eventId);
    }
}
