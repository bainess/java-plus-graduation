package ru.practicum.analyzer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.analyzer.similarity.model.EventSimilarity;
import ru.practicum.analyzer.similarity.repository.SimilarityRepository;
import ru.practicum.analyzer.useraction.model.UserAction;
import ru.practicum.analyzer.useraction.repository.UserActionRepository;
import ru.practicum.ewm.stats.service.dashboard.RecommendedEventProto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final UserActionRepository userActionRepository;
    private final SimilarityRepository similarityRepository;

    public List<RecommendedEventProto> getRecommendationsForUser(long userId, int maxResult) {
        Instant now = Instant.now();
        Instant weekAgo = now.minus(7, ChronoUnit.DAYS);

        List<UserAction> eventsUserInteracted = userActionRepository
                .findTopNByUserIdAndTimestampBetweenOrderByTimestampDesc(userId, weekAgo, now, maxResult);
        if (eventsUserInteracted.isEmpty()) return List.of();

        Map<Long, List<EventSimilarity>> similaritiesByEvent =
                eventsUserInteracted.stream()
                        .collect(Collectors.toMap(
                                UserAction::getEventId,
                                action -> getSimilaritiesUserNotInteracted(
                                        action.getEventId(),
                                        userId,
                                        maxResult
                                )
                        ));

        List<RecommendedEventProto> recommendations = new ArrayList<>();

        for (Map.Entry<Long, List<EventSimilarity>> entry : similaritiesByEvent.entrySet()) {

            long sourceEventId = entry.getKey();
            List<EventSimilarity> similarities = entry.getValue();

            for (EventSimilarity similarity : similarities) {

                long recommendedEventId =
                        getOtherEventId(similarity, sourceEventId);

                RecommendedEventProto recommendation =
                        RecommendedEventProto.newBuilder()
                                .setEventId(recommendedEventId)
                                .setScore(similarity.getScore())
                                .build();

                recommendations.add(recommendation);
            }
        }
        return recommendations;
    }

    private long getOtherEventId(EventSimilarity similarity, long eventId) {
        if (similarity.getEventA() == eventId) {
            return similarity.getEventB();
        }

        return similarity.getEventA();
    }

    public List<RecommendedEventProto> getSimilarEvents(long eventId, long userId, int maxResult) {
        List<EventSimilarity> unseenSimilarities = getSimilaritiesUserNotInteracted(eventId, userId, maxResult);

        List<RecommendedEventProto> result = new ArrayList<>();

        for (EventSimilarity similarity : unseenSimilarities) {
            Long id = (eventId == similarity.getEventA())
                    ? similarity.getEventB() : similarity.getEventA();
            RecommendedEventProto res = RecommendedEventProto.newBuilder().setEventId(id).setScore(similarity.getScore()).build();
            result.add(res);
        }
        return result;
    }

    private List<EventSimilarity> getSimilaritiesUserNotInteracted(Long eventId, Long userId, int maxResult) {
        List<EventSimilarity> eventIds = similarityRepository.findSimilarEvents(eventId);

        Set<Long> eventUserInteracted = userActionRepository.findEventIdsByUserId(userId);

        List<EventSimilarity> unseenSimilarities = eventIds.stream().filter(similarity ->
                {
                    long similarEventId = eventId == similarity.getEventA()
                            ? similarity.getEventB()
                            : similarity.getEventA();

                    return !eventUserInteracted.contains(similarEventId);
                })
                .sorted(Comparator.comparing(EventSimilarity::getScore).reversed())
                .limit(maxResult)
                .toList();
        return unseenSimilarities;
    }

    public List<RecommendedEventProto> getEventsRatings(List<Long> eventIds) {
        return eventIds.stream().map(eventId -> RecommendedEventProto.newBuilder()
                .setEventId(eventId)
                .setScore(userActionRepository.sumRatingByEventId(eventId))
                .build()).toList();
    }
}
