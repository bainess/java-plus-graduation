package ru.practicum.analyzer.similarity.model;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
@EqualsAndHashCode
public class EventSimilarityId {
    long eventA;
    long eventB;
}
