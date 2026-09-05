package ru.yandex.practicum.eventservice.location.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.explorewithme.shareddto.dto.location.LocationDto;
import ru.yandex.practicum.eventservice.location.dto.NewLocationRequest;
import ru.yandex.practicum.eventservice.location.dto.UpdateLocationRequest;
import ru.yandex.practicum.eventservice.location.model.Location;


@UtilityClass
public class LocationMapper {
    public static Location toEntity(NewLocationRequest request) {
        return Location.builder()
                .name(request.getName())
                .lat(request.getLat())
                .lon(request.getLon())
                .radius(request.getRadius())
                .build();
    }

    public static LocationDto toDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .lat(location.getLat())
                .lon(location.getLon())
                .radius(location.getRadius())
                .build();
    }

    public static void updateEntity(UpdateLocationRequest request, Location location) {
        if (request.getName() != null) {
            location.setName(request.getName());
        }
        if (request.getLat() != null) {
            location.setLat(request.getLat());
        }
        if (request.getLon() != null) {
            location.setLon(request.getLon());
        }
        if (request.getRadius() != null) {
            location.setRadius(request.getRadius());
        }
    }
}
