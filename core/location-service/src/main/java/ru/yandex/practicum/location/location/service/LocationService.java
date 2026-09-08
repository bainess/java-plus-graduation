package ru.yandex.practicum.location.location.service;


import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
import ru.practicum.explorewithme.shareddto.dto.location.LocationDto;
import ru.yandex.practicum.location.location.dto.NewLocationRequest;
import ru.yandex.practicum.location.location.dto.UpdateLocationRequest;

import java.util.List;

public interface LocationService {
    LocationDto createLocation(NewLocationRequest request);

    LocationDto updateLocation(Long locId, UpdateLocationRequest request);

    void deleteLocation(Long locId);

    LocationDto getLocationById(Long locId);

    List<LocationDto> getAllLocations(int from, int size);

    List<EventFullDto> getEventsByLocation(Long searchLocId, int from, int size);
}
