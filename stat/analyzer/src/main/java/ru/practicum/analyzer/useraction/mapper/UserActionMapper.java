package ru.practicum.analyzer.useraction.mapper;

import ru.practicum.analyzer.useraction.model.UserAction;
import ru.practicum.analyzer.useraction.model.UserActionId;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

public class UserActionMapper {
    public UserAction mapToUserAction(UserActionAvro avro) {
        UserActionId id = new UserActionId(avro.getUserId(), avro.getEventId());

        return new UserAction(id, getWeight(avro.getActionType()), avro.getTimestamp());
    }

    private double getWeight(ActionTypeAvro actionType) {
        return switch (actionType) {
            case VIEW -> 0.4;
            case REGISTER -> 0.8;
            case LIKE -> 1.0;
        };
    }
}
