package ru.yandex.practicum.requestservice.service;

import ru.practicum.explorewithme.shareddto.dto.request.ConfirmedRequestsCount;
import ru.yandex.practicum.requestservice.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.requestservice.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.requestservice.dto.ParticipationRequestDto;

import java.util.List;

public interface EventRequestService {
    List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateEventRequests(Long userId, Long eventId,
                                                       EventRequestStatusUpdateRequest request);

    ParticipationRequestDto saveEventParticipation(Long userId, Long eventId);

    List<ParticipationRequestDto> getUserEvents(Long userId);

    ParticipationRequestDto removeParticipation(Long userId, Long requestId);

    Integer getConfirmedRequests(Long eventId);

    List<ConfirmedRequestsCount> getConfirmedRequestsByEventIds(List<Long> eventIds);
}
