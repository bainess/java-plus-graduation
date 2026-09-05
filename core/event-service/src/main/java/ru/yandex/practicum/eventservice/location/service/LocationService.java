package ru.yandex.practicum.eventservice.location.service;



import ru.practicum.explorewithme.shareddto.dto.location.LocationDto;
import ru.yandex.practicum.eventservice.location.dto.NewLocationRequest;
import ru.yandex.practicum.eventservice.location.dto.UpdateLocationRequest;

import java.util.List;

public interface LocationService {
    LocationDto createLocation(NewLocationRequest request);

    LocationDto updateLocation(Long locId, UpdateLocationRequest request);

    void deleteLocation(Long locId);

    LocationDto getLocationById(Long locId);

    List<LocationDto> getAllLocations(int from, int size);
}
