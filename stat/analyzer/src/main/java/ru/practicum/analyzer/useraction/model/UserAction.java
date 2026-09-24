package ru.practicum.analyzer.useraction.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Entity
@Table(name = "user_actions")
public class UserAction {

    @EmbeddedId
    UserActionId id;
    double weight;
    Instant timestamp;
}
