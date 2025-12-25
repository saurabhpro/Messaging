package com.saurabh.kafka.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    public static final String TOPIC_NAME = "events";
    public static final String GROUP_ID = "events-consumer-group";

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void consume(ConsumerRecord<String, Object> record) {
        log.info("Received message from partition [{}] with offset [{}]",
                record.partition(), record.offset());
        log.info("Key: {}, Value: {}", record.key(), record.value());
    }
}
