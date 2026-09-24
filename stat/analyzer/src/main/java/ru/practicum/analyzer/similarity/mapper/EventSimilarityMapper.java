package ru.practicum.analyzer.similarity.mapper;

import ru.practicum.analyzer.similarity.model.EventSimilarity;
import ru.practicum.analyzer.similarity.model.EventSimilarityId;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

public class EventSimilarityMapper {
    public  EventSimilarity mapToEventSimilarity(EventSimilarityAvro avro) {
        EventSimilarityId id = new EventSimilarityId(
                avro.getEventA(),
                avro.getEventB()
        );

        return new EventSimilarity(
                id,
                avro.getScore(),
                avro.getTimestamp()
        );
    }
}
