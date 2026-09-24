package ru.practicum.analyzer.similarity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.analyzer.similarity.mapper.EventSimilarityMapper;
import ru.practicum.analyzer.similarity.model.EventSimilarity;
import ru.practicum.analyzer.similarity.repository.SimilarityRepository;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

@Service
@RequiredArgsConstructor
public class SimilarityService {
    private final EventSimilarityMapper mapper;
    private final SimilarityRepository repository;

    public EventSimilarity saveSimilarity(EventSimilarityAvro avro) {
        EventSimilarity similarity =  mapper.mapToEventSimilarity(avro);
        return repository.save(similarity);

    }
}
