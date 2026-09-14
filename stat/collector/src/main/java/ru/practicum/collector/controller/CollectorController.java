package ru.practicum.collector.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.collector.kafka.CollectorKafkaProducer;
import ru.practicum.collector.mapper.UserActionMapper;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import stats.service.collector.UserActionProto;

@Component
@RequiredArgsConstructor
public class CollectorController {
    private final CollectorKafkaProducer producer;

    public void sendMessage(UserActionProto proto) {
        UserActionAvro avro = UserActionMapper.mapToUserActionAvro(proto);
        producer.send(avro);
    }
}
