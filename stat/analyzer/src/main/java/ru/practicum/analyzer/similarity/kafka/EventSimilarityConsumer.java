package ru.practicum.analyzer.similarity.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.practicum.analyzer.similarity.service.SimilarityService;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventSimilarityConsumer {
    private static final String EVENT_SIMILARITY_TOPIC = "stats.events-similarity.v1";
    private final KafkaConsumer<String, SpecificRecordBase> kafkaConsumer;
    private final SimilarityService similarityService;

    public void start() {
        try {
            kafkaConsumer.subscribe(List.of(EVENT_SIMILARITY_TOPIC));

            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records =
                        kafkaConsumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    EventSimilarityAvro similarity = (EventSimilarityAvro) record.value();

                    log.info("Received similarity: eventA={}, eventB={}, similarity={}, timestamp={}",
                            similarity.getEventA(),
                            similarity.getEventB(),
                            similarity.getScore(),
                            similarity.getTimestamp());

                    similarityService.saveSimilarity(similarity);
                }
                kafkaConsumer.commitSync();
            }
        } catch (WakeupException ignored) {
        } catch (Exception e) {
            log.error("Handling event error", e);
        } finally {
            try {
                kafkaConsumer.commitSync();
            } finally {
                kafkaConsumer.close();
                log.info("Consumer closed");
            }
        }
    }


}
