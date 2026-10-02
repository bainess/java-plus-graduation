package ru.practicum.collector.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import stats.service.collector.UserActionControllerGrpc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CollectorKafkaProducer {
    private static final String USER_ACTIONS_TOPIC = "stats.user-actions.v1";
    private final Producer<Long, UserActionAvro> producer;

    public void send(UserActionAvro userAction) {
        if (userAction == null) {
            log.error("Cannot send null of user action");
            return;
        }
        Long key = userAction.getEventId();
        log.info(
                "Sending user action: topic={}, key={}, value={}",
                USER_ACTIONS_TOPIC,
                key,
                userAction
        );
        ProducerRecord<Long, UserActionAvro> record = new ProducerRecord<>(USER_ACTIONS_TOPIC, key, userAction);
        try {
            RecordMetadata metadata = producer
                    .send(record)
                    .get(5, TimeUnit.SECONDS);
            log.info(
                    "User action sent: topic={}, partition={}, offset={}",
                    metadata.topic(),
                    metadata.partition(),
                    metadata.offset()
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Kafka send interrupted", e);
            throw  new RuntimeException("Kafka send interrupted", e);
        } catch (TimeoutException e) {
            log.error("Kafka send timeout", e);
            throw new RuntimeException("Kafka send timeout", e);
        }catch (Exception e) {
            log.error("Failed to send user action to Kafka", e);
            throw new RuntimeException("Failed to send user action to Kafka", e);
        }
    }
}
