package ru.practicum.aggregator.kafka;


import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;

public class AvroCommonDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {
    private final DecoderFactory decoderFactory;
    private final DatumReader<T> reader;

    public AvroCommonDeserializer(Schema schema) {
        this(DecoderFactory.get(), schema);
    }

    public AvroCommonDeserializer(DecoderFactory decoderFactory, Schema schema) {
        this.decoderFactory = decoderFactory;
        this.reader = new SpecificDatumReader<>(schema);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            if (data != null) {
                BinaryDecoder decoder = decoderFactory.binaryDecoder(data, null);
                return this.reader.read(null, decoder);
            }
            return null;
        } catch (Exception e) {
           // throw new DeserializationException("Ошибка десериализации данных из топика [" + topic + "]: " + e.getMessage());
        throw new IllegalArgumentException("Deseriqalization mistake");
        }
    }
}


//
//import org.apache.avro.io.DatumReader;
//import org.apache.avro.io.Decoder;
//import org.apache.avro.io.DecoderFactory;
//import org.apache.avro.specific.SpecificDatumReader;
//import org.apache.kafka.common.serialization.Deserializer;
//import ru.practicum.ewm.stats.avro.UserActionAvro;
//
//import java.io.IOException;
//
//public class KafkaDeserializer
//        implements Deserializer<UserActionAvro> {
//
//    private final DatumReader<UserActionAvro> reader =
//            new SpecificDatumReader<>(UserActionAvro.getClassSchema());
//
//    private final DecoderFactory decoderFactory =
//            DecoderFactory.get();
//
//    @Override
//    public UserActionAvro deserialize(String topic, byte[] bytes) {
//        if (bytes == null) {
//            return null;
//        }
//
//        try {
//            Decoder decoder = decoderFactory.binaryDecoder(bytes, null);
//            return reader.read(null, decoder);
//        } catch (IOException e) {
//            throw new RuntimeException(
//                    "Avro-message deserialization mistake", e
//            );
//        }
//    }
//}
