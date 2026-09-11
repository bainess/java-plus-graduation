package ru.yandex.practicum.eventservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import ru.practicum.explorewithme.shareddto.dto.location.LocationDto;
import ru.yandex.practicum.eventservice.feign.fallback.LocationServiceFallback;

import java.util.Optional;

@FeignClient(name = "location-service",
fallbackFactory = LocationServiceFallback.class)
public interface LocationClient {
    @GetMapping("admin/locations/{locId}")
    Optional<LocationDto> findById(Long locId);
}
