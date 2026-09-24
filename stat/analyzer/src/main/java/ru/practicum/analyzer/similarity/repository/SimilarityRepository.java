package ru.practicum.analyzer.similarity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.analyzer.similarity.model.EventSimilarity;
import ru.practicum.analyzer.similarity.model.EventSimilarityId;

import java.util.List;

public interface SimilarityRepository extends JpaRepository<EventSimilarity, EventSimilarityId> {

    @Query("""
    SELECT es
    FROM EventSimilarity es
    WHERE es.id.eventA = :eventId
       OR es.id.eventB = :eventId
    ORDER BY es.score DESC
    """)
    List<EventSimilarity> findSimilarEvents(
            @Param("eventId") long eventId
    );


}
