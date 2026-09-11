package ru.yandex.practicum.requestservice.mapper;


import ru.practicum.explorewithme.shareddto.dto.user.UserShortDto;
import ru.yandex.practicum.requestservice.dto.ParticipationRequestDto;
import ru.yandex.practicum.requestservice.model.ParticipationRequest;

public final class ParticipationRequestMapper {

    public static ParticipationRequestDto toDto(ParticipationRequest request, UserShortDto user) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .requester(user.getId())
                .event(request.getEventId())
                .status(request.getStatus())
                .created(request.getCreated())
                .build();
    }
}
