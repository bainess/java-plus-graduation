package ru.practicum.analyzer.useraction.repository;

import org.checkerframework.checker.units.qual.N;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.analyzer.useraction.model.UserAction;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {

    List<UserAction> findTopNByUserIdAndTimestampBetweenOrderByTimestampDesc(
            Long userId,
            Instant weekAgo,
            Instant now,
            int N
            );

    List<UserAction> findByIdUserIdOrderByTimestampDesc(
            long userId,
            Pageable pageable
    );

    boolean existsByIdUserIdAndIdEventId(
            long userId,
            long eventId
    );

    Set<Long> findEventIdsByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(u.rating), 0) FROM UserAction u WHERE u.eventId = :eventId")
    Double sumRatingByEventId(Long eventId);
}
