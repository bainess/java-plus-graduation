package ru.practicum.analyzer.similarity.mapper;

import ru.practicum.analyzer.similarity.model.EventSimilarity;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

public class EventSimilarityMapper {
    public EventSimilarity mapToEventSimilarity(EventSimilarityAvro avro) {

        return EventSimilarity.builder().
                eventA(avro.getEventA()).
                eventB(avro.getEventB()).
                score(avro.getScore()).
                timestamp(avro.getTimestamp())
                .build();
    }
}
