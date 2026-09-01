package ru.practicum.explorewithme.service.request.service;


import ru.practicum.explorewithme.service.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.service.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.service.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.shareddto.dto.request.ConfirmedRequestsCount;

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
