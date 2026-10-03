package ru.practicum.aggregator.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.practicum.aggregator.service.SimilarityService;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserActionConsumer {
    private static final String USER_ACTIONS_TOPIC = "stats.user-actions.v1";
    private static final String EVENT_SIMILARITY_TOPIC = "stats.events-similarity.v1";
    private final KafkaConsumer<Long, SpecificRecordBase> kafkaConsumer;
    private final KafkaTemplate<Long, SpecificRecordBase> producer;
    private final SimilarityService service;

    public void start() {
try {
    kafkaConsumer.subscribe(List.of(USER_ACTIONS_TOPIC));

    while (true) {
        ConsumerRecords<Long, SpecificRecordBase> records =
                kafkaConsumer.poll(Duration.ofMillis(100));

        for (ConsumerRecord<Long, SpecificRecordBase> record : records) {
            UserActionAvro action = (UserActionAvro) record.value();

            log.info("Received action: userId={}, eventId={}, actionType={}, timestamp={}",
                    action.getUserId(), action.getEventId(), action.getActionType(), action.getTimestamp());

            List<EventSimilarityAvro> similarities = service.process(action);
            if (similarities.isEmpty()) {
                continue;
            }

            for (EventSimilarityAvro data : similarities) {
                sendSimilarityData(data);
            }
        }
        kafkaConsumer.commitSync();
    }
} catch (WakeupException ignored) {
} catch (Exception e) {
    log.error("Handling event error", e);
} finally {
    try {
        producer.flush();
    } finally {
        kafkaConsumer.close();
        log.info("Consumer closed");
        producer.destroy();
        log.info("Producer closed");
    }
}
    }

    private void sendSimilarityData(EventSimilarityAvro data) {
        ProducerRecord<Long, SpecificRecordBase> record =
                new ProducerRecord<>(
                        EVENT_SIMILARITY_TOPIC,
                        data
                );

        try{
            producer.send(record).get();
            log.info(
                    "Similarity sent: eventA={}, eventB={}, score={}",
                    data.getEventA(),
                    data.getEventB(),
                    data.getScore()
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sending similarity was interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to send similarity", e);
        }
    }
}
