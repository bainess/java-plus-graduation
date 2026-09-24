package ru.practicum.analyzer.useraction.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.analyzer.useraction.model.UserAction;
import ru.practicum.analyzer.useraction.model.UserActionId;

import java.util.List;
import java.util.Optional;

public interface UserActionRepository extends JpaRepository<UserAction, UserActionId> {

    List<UserAction> findByIdUserId(long userId);

    List<UserAction> findByIdUserIdOrderByTimestampDesc(
            long userId,
            Pageable pageable
    );

    boolean existsByIdUserIdAndIdEventId(
            long userId,
            long eventId
    );

    Optional<UserAction> findByIdUserIdAndIdEventId(
            long userId,
            long eventId
    );

    @Query("""
    SELECT ua.id.eventId, SUM(ua.weight)
    FROM UserAction ua
    WHERE ua.id.eventId IN :eventIds
    GROUP BY ua.id.eventId
    """)
    List<Object[]> findInteractionCounts(
            @Param("eventIds") List<Long> eventIds
    );
}
