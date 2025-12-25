package com.saurabh.kafka.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saurabh.kafka.producer.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public CompletableFuture<Void> sendMessage(String key, Object message) {
        log.info("Sending message with key [{}]: {}", key, message);

        String jsonMessage;
        try {
            jsonMessage = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize message", e);
            return CompletableFuture.failedFuture(e);
        }

        return kafkaTemplate.send(KafkaConfig.TOPIC_NAME, key, jsonMessage)
                .thenAccept(result -> {
                    var metadata = result.getRecordMetadata();
                    log.info("Message sent to partition [{}] with offset [{}]",
                            metadata.partition(), metadata.offset());
                })
                .exceptionally(ex -> {
                    log.error("Failed to send message", ex);
                    return null;
                });
    }
}
