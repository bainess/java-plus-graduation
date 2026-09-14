package ru.practicum.collector.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import stats.service.collector.UserActionControllerGrpc;

@Slf4j
@Component
@RequiredArgsConstructor
public class CollectorKafkaProducer {
    private static final String USER_ACTIONS_TOPIC = "stats.user-actions.v1";
    private final Producer<String, SpecificRecordBase> producer;

    public void send(UserActionAvro userAction) {
        if (userAction == null) {
            log.error("Cannot send null of user action");
            return;
        }
        String key = String.valueOf(userAction.getEventId());
        log.info("Sending user action to topic '{}", USER_ACTIONS_TOPIC);
        ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(USER_ACTIONS_TOPIC, key, userAction);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                log.error("Failed to send user action to Kafka", exception);
            } else {
                log.info("User action sent: topic={}, partition={}, offset={}",
                        metadata.topic(), metadata.partition(), metadata.offset());
            }
        });
    }
}
