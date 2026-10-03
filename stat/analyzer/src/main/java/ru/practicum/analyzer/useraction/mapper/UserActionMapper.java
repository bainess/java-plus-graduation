package ru.practicum.analyzer.useraction.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.analyzer.useraction.model.UserAction;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

@Component
public class UserActionMapper {
    public UserAction mapToUserAction(UserActionAvro avro) {
        return UserAction.builder()
                .userId(avro.getUserId())
                .eventId(avro.getEventId())
                .rating(getWeight(avro.getActionType()))
                .timestamp(avro.getTimestamp())
                .build();
    }

    private double getWeight(ActionTypeAvro actionType) {
        return switch (actionType) {
            case VIEW -> 0.4;
            case REGISTER -> 0.8;
            case LIKE -> 1.0;
        };
    }
}
