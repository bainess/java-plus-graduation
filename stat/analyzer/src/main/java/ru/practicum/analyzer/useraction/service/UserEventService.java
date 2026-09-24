package ru.practicum.analyzer.useraction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.analyzer.useraction.mapper.UserActionMapper;
import ru.practicum.analyzer.useraction.model.UserAction;
import ru.practicum.analyzer.useraction.repository.UserActionRepository;
import ru.practicum.ewm.stats.avro.UserActionAvro;

@Service
@RequiredArgsConstructor
public class UserEventService {
    private final UserActionMapper mapper;
    private final UserActionRepository repository;

    public UserAction saveUserAction(UserActionAvro avro) {
        UserAction action = mapper.mapToUserAction(avro);

        return repository.save(action);
    }
}
