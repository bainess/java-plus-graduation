package ru.practicum.aggregator.kafka;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroCommonSerializer implements Serializer<SpecificRecord> {
    private final EncoderFactory encoderFactory = EncoderFactory.get();

    @Override
    public byte[] serialize(String topic, SpecificRecord data) {
        if (data == null) {
            return null;
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BinaryEncoder encoder = encoderFactory.binaryEncoder(outputStream, null);
            DatumWriter<SpecificRecord> datumWriter = new SpecificDatumWriter<>(data.getSchema());

            datumWriter.write(data, encoder);
            encoder.flush();

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Ошибка сериализации экземпляра класса " + data.getClass(), e);
        }
    }
}


//
//import lombok.Generated;
//import org.apache.avro.io.BinaryEncoder;
//import org.apache.avro.io.DatumWriter;
//import org.apache.avro.io.EncoderFactory;
//import org.apache.avro.specific.SpecificDatumWriter;
//import org.apache.avro.specific.SpecificRecordBase;
//import org.apache.kafka.common.errors.SerializationException;
//import org.apache.kafka.common.serialization.Serializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//public class KafkaSerializer implements Serializer<SpecificRecordBase> {
//    @Generated
//    private static final Logger log = LoggerFactory.getLogger(KafkaSerializer.class);
//    private final EncoderFactory encoderFactory = EncoderFactory.get();
//
//    public byte[] serialize(String topic, SpecificRecordBase data) {
//        if (data == null) {
//            return null;
//        } else {
//            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//                BinaryEncoder encoder = this.encoderFactory.binaryEncoder(out, (BinaryEncoder)null);
//                DatumWriter<SpecificRecordBase> writer = new SpecificDatumWriter(data.getSchema());
//                writer.write(data, encoder);
//                encoder.flush();
//                return out.toByteArray();
//            } catch (IOException ex) {
//                throw new SerializationException("Ошибка сериализации данных для топика [" + topic + "]", ex);
//            }
//        }
//    }
//}
