package ru.yandex.practicum.eventservice.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.shareddto.dto.location.LocationDto;
import ru.yandex.practicum.eventservice.feign.client.LocationClient;
import ru.yandex.practicum.eventservice.feign.error.LocationServiceNotAvailableException;

import java.util.Optional;

@Slf4j
@Component
public class LocationServiceFallback implements FallbackFactory<LocationClient> {
    @Override
    public LocationClient create(Throwable cause) {
        return new LocationClient() {
            @Override
            public Optional<LocationDto> findById(Long locId) {
                log.warn("FALLBACK: Location service unavailable, cause");
                throw new LocationServiceNotAvailableException(locId, cause);
            }
        };
    }
}
