package ru.practicum.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimilarityService {
    //хранит веса пользователей, взаимодействовавших с меропрятием(и)
    private final Map<Long, Map<Long, Double>> userActionWeights;
    // хранит сумму весов мероприятия
    private final Map<Long, Double> eventWeightsSums;
    // хранит сумму минимальных весов, для пар мероприятий
    private final Map<Long, Map<Long, Double>> minWeightsSums;

    public List<EventSimilarityAvro> process(UserActionAvro action) {
        Long eventA = action.getEventId();

        double oldWeight = userActionWeights
                .getOrDefault(eventA, Map.of())
                .getOrDefault(action.getUserId(), 0.0);
        double newWeight = getWeight(action.getActionType());
        double difference = newWeight - oldWeight;

        if (newWeight <= oldWeight) {
            return List.of();
        }

        saveNewWeight(action);
        saveMinWeightsSums(action, oldWeight, newWeight);
        saveEventWeightSum(action, difference);

        List<EventSimilarityAvro> result = new ArrayList<>();

        for (Long eventB : userActionWeights.keySet()) {
            if (eventA.equals(eventB)) {
                continue;
            }
            double score = countSimilarity(eventA,eventB);
            log.info("Similarity for id={}", result);

            if (score == 0.0) {
                continue;
            }
            long first = Math.min(eventA, eventB);
            long second = Math.max(eventA, eventB);

            result.add(EventSimilarityAvro.newBuilder()
                    .setEventA(first)
                    .setEventB(second)
                    .setScore(score)
                    .setTimestamp(action.getTimestamp())
                    .build());
        }
        return result;
    }

    // рассчитывает сходство двух мероприятий
    private double countSimilarity(Long eventA, Long eventB) {
        double numerator = getMinWeightSum(eventA, eventB);
        double denominator = eventWeightsSums.get(eventA) * eventWeightsSums.get(eventB);
        if (denominator == 0.0) return 0.0;
        return numerator / Math.sqrt(denominator);
    }

    // считает сумму минимальных весов всех пользователей,
    // взаимодействовавших с обоими сравниваемыми мероприятиями
    private void saveMinWeightsSums(UserActionAvro action, double oldWeightA, double newWeightA) {
        for (Map.Entry<Long, Map<Long, Double>> entry : userActionWeights.entrySet()) {
            long eventB = entry.getKey();

            if (action.getEventId() == eventB) {
                continue;
            }
            Double weightB = entry.getValue().get(action.getUserId());

            if (weightB == null) {
                continue;
            }

            double olnMin = Math.min(oldWeightA, weightB);
            double newMin = Math.min(newWeightA, weightB);

            double difference = newMin - olnMin;

            if (difference > 0) {
                double oldSum = getMinWeightSum(action.getEventId(), eventB);
                putMinWeightSum(action.getEventId(), eventB, oldSum + difference);
            }
        }
    }

    public void putMinWeightSum(long eventA, long eventB, double sum) {
        long first = Math.min(eventA, eventB);
        long second = Math.max(eventA, eventB);

        minWeightsSums
                .computeIfAbsent(first, e -> new HashMap<>())
                .put(second, sum);
    }

    public Double getMinWeightSum(long eventA, long eventB) {
        long first = Math.min(eventA, eventB);
        long second = Math.max(eventA, eventB);

        return minWeightsSums
                .computeIfAbsent(first, e -> new HashMap<>())
                .getOrDefault(second, 0.0);
    }


    // считает и сохраняет сумму весов для каждого события (событие посчитано, добавляется разница)
    private void saveEventWeightSum(UserActionAvro action, double difference) {
        eventWeightsSums.merge(
                action.getEventId(),
                difference,
                Double::sum
        );

    }


    // считает и сохраняет новые веса в userActionWeights
    private void saveNewWeight(UserActionAvro userAction) {
        userActionWeights
                .computeIfAbsent(userAction.getEventId(), e -> new HashMap<>())
                .put(
                        userAction.getUserId(),
                        getWeight(userAction.getActionType())
                );
    }


    // преобразует ActionType в веса
    private double getWeight(ActionTypeAvro actionType) {
        return switch (actionType) {
            case VIEW -> 0.4;
            case REGISTER -> 0.8;
            case LIKE -> 1.0;
        };
    }

}
