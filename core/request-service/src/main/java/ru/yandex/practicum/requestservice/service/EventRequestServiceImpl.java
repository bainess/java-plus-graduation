package ru.yandex.practicum.requestservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.shareddto.dto.event.EventFullDto;
import ru.practicum.explorewithme.shareddto.dto.request.ConfirmedRequestsCount;
import ru.practicum.explorewithme.shareddto.dto.user.UserShortDto;
import ru.practicum.explorewithme.shareddto.enums.EventState;
import ru.practicum.explorewithme.shareddto.enums.ParticipationRequestStatus;
import ru.practicum.explorewithme.shareddto.exception.ConflictException;
import ru.practicum.explorewithme.shareddto.exception.NotFoundException;
import ru.yandex.practicum.requestservice.dal.EventRequestRepository;
import ru.yandex.practicum.requestservice.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.requestservice.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.requestservice.dto.ParticipationRequestDto;
import ru.yandex.practicum.requestservice.feign.EventClient;
import ru.yandex.practicum.requestservice.feign.UserClient;
import ru.yandex.practicum.requestservice.mapper.ParticipationRequestMapper;
import ru.yandex.practicum.requestservice.model.ParticipationRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventRequestServiceImpl implements EventRequestService {

    private final EventClient eventClient;
    private final EventRequestRepository eventRequestRepository;
    private final UserClient userClient;

    @Override
    public List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId) {
        log.info("Получение заявок на событие id={} пользователя id={}", eventId, userId);
        EventFullDto event = eventClient.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено или недоступно"));
        List<ParticipationRequest> requests = eventRequestRepository
                .findAllByEventId(eventId);
        List<Long> requesterIds = requests.stream().map(ParticipationRequest::getRequesterId).toList();
        Map<Long, UserShortDto> requesters = userClient.findAllById(requesterIds);
        return requests.stream()
                .map(request -> {
                    return ParticipationRequestMapper.toDto(request, requesters.get(request.getRequesterId()));
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateEventRequests(Long userId, Long eventId,
                                                              EventRequestStatusUpdateRequest request) {
        log.info("Изменение статуса заявок на событие id={} пользователем id={}", eventId, userId);

        EventFullDto event = getEventAndValidateOwnership(userId, eventId);
        validateRequestPrerequisites(event);

        ParticipationRequestStatus newStatus = validateNewStatus(request.getStatus());
        List<ParticipationRequest> pendingRequests = getPendingRequestsOrThrow(request.getRequestIds());

        List<ParticipationRequest> confirmed = new ArrayList<>();
        List<ParticipationRequest> rejected = new ArrayList<>();

        if (newStatus == ParticipationRequestStatus.CONFIRMED) {
            processConfirmation(event, pendingRequests, confirmed, rejected);
        } else {
            rejectAll(pendingRequests, rejected);
        }

        eventRequestRepository.saveAll(pendingRequests);

        return buildResult(confirmed, rejected);
    }

    private EventFullDto getEventAndValidateOwnership(Long userId, Long eventId) {
        return eventClient.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено или недоступно"));
    }

    private void validateRequestPrerequisites(EventFullDto event) {
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new ConflictException("Подтверждение заявок не требуется для данного события");
        }
    }

    private ParticipationRequestStatus validateNewStatus(ParticipationRequestStatus status) {
        if (status != ParticipationRequestStatus.CONFIRMED && status != ParticipationRequestStatus.REJECTED) {
            throw new ConflictException("Неверный статус заявки");
        }
        return status;
    }

    private List<ParticipationRequest> getPendingRequestsOrThrow(List<Long> requestIds) {
        List<ParticipationRequest> requests = eventRequestRepository.findAllByIdInAndStatus(
                requestIds, ParticipationRequestStatus.PENDING);
        if (requests.size() != requestIds.size()) {
            throw new ConflictException("Не все заявки находятся в состоянии ожидания");
        }
        return requests;
    }

    private void processConfirmation(EventFullDto event, List<ParticipationRequest> requests,
                                     List<ParticipationRequest> confirmed, List<ParticipationRequest> rejected) {
        int currentConfirmed = eventRequestRepository.countByEventIdAndStatus(
                event.getId(), ParticipationRequestStatus.CONFIRMED);
        int limit = event.getParticipantLimit();
        int remaining = limit - currentConfirmed;

        for (ParticipationRequest r : requests) {
            if (remaining > 0) {
                r.setStatus(ParticipationRequestStatus.CONFIRMED);
                confirmed.add(r);
                remaining--;
            } else {
                r.setStatus(ParticipationRequestStatus.REJECTED);
                rejected.add(r);
            }
        }

        // Автоматически отклонить все оставшиеся PENDING заявки, если лимит исчерпан
        if (remaining == 0) {
            rejectRemainingPending(requests, rejected);
        }
    }

    private void rejectRemainingPending(List<ParticipationRequest> processedRequests,
                                        List<ParticipationRequest> rejectedContainer) {
        // Получить заявки, которые всё ещё в статусе PENDING из исходного списка
        List<Long> pendingIds = processedRequests.stream()
                .filter(r -> r.getStatus() == ParticipationRequestStatus.PENDING)
                .map(ParticipationRequest::getId)
                .collect(Collectors.toList());
        if (!pendingIds.isEmpty()) {
            List<ParticipationRequest> stillPending = eventRequestRepository.findAllByIdInAndStatus(
                    pendingIds, ParticipationRequestStatus.PENDING);
            for (ParticipationRequest r : stillPending) {
                r.setStatus(ParticipationRequestStatus.REJECTED);
                rejectedContainer.add(r);
            }
        }
    }

    private void rejectAll(List<ParticipationRequest> requests, List<ParticipationRequest> rejected) {
        for (ParticipationRequest r : requests) {
            r.setStatus(ParticipationRequestStatus.REJECTED);
            rejected.add(r);
        }
    }

    private EventRequestStatusUpdateResult buildResult(List<ParticipationRequest> confirmed,
                                                       List<ParticipationRequest> rejected) {
        List<Long> confirmedUserIds = confirmed.stream().map(ParticipationRequest::getRequesterId).toList();
        Map<Long, UserShortDto> confirmedUsers= userClient.findAllById(confirmedUserIds);
        List<Long> rejectedUserIds = rejected.stream().map(ParticipationRequest::getRequesterId).toList();
        Map<Long, UserShortDto> rejectedUsers = userClient.findAllById(rejectedUserIds);
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmed.stream().map(request -> ParticipationRequestMapper.toDto(request, confirmedUsers.get(request.getRequesterId()))).collect(Collectors.toList()))
                .rejectedRequests(rejected.stream().map(request -> ParticipationRequestMapper.toDto(request, rejectedUsers.get(request.getRequesterId()))).collect(Collectors.toList()))
                .build();
    }

    @Transactional(readOnly = false)
    public ParticipationRequestDto saveEventParticipation(Long userId, Long eventId) {
        if (eventRequestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ConflictException("Запрос на добавление пользователя" + userId + "на событие " + eventId + " уже существует");
        }
        UserShortDto participant = userClient.findById(userId);
        EventFullDto event = eventClient.findEventById(eventId).orElseThrow(() -> new NotFoundException("Событие " + eventId + " не найдено"));

        if (event.getInitiator().equals(userId))
            throw new ConflictException("Инициатор не может присылать запрос на свое событие");

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя добавиться в неопубликованное событие");
        }
        ParticipationRequest request = ParticipationRequest.builder()
                .requesterId(participant.getId())
                .eventId(event.getId())
                .created(LocalDateTime.now())
                .build();

        Integer numParticipants = eventRequestRepository.countByEventId(eventId);
        log.info("limit={}, confirmed={}", event.getParticipantLimit(), numParticipants);

        if (event.getParticipantLimit() == 0) {
            request.setStatus(ParticipationRequestStatus.CONFIRMED);
        } else if (event.getParticipantLimit() > numParticipants) {
            request.setStatus(ParticipationRequestStatus.PENDING);
        } else {
            throw new ConflictException("Количество участников события не может превышать " + event.getParticipantLimit());
        }
        return ParticipationRequestMapper.toDto(eventRequestRepository.save(request), participant);
    }

    public List<ParticipationRequestDto> getUserEvents(Long userId) {
        UserShortDto user = userClient.findById(userId);
        return eventRequestRepository.findAllByRequesterId(userId).stream()
                .map(request -> ParticipationRequestMapper.toDto(request, user))
                .toList();
    }

    public ParticipationRequestDto removeParticipation(Long userId, Long requestId) {
        ParticipationRequest request = eventRequestRepository.findByIdAndRequesterId(requestId, userId);
        eventRequestRepository.delete(request);
        request.setStatus(ParticipationRequestStatus.CANCELED);
        UserShortDto user = userClient.findById(userId);
        return ParticipationRequestMapper.toDto(request, user);
    }

    @Override
    public Integer getConfirmedRequests(Long eventId) {
        return eventRequestRepository.countByEventIdAndStatus(eventId, ParticipationRequestStatus.CONFIRMED);
    }

    @Override
    public List<ConfirmedRequestsCount> getConfirmedRequestsByEventIds(List<Long> eventIds) {
        log.info("number of confirmed requests {}", eventRequestRepository.countConfirmedRequestsByEventIds(eventIds).size());
        return eventRequestRepository.countConfirmedRequestsByEventIds(eventIds);
    }
}
