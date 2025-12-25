package com.saurabh.kafka.producer.controller;

import com.saurabh.kafka.producer.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducerService producerService;

    @PostMapping("/publish")
    public ResponseEntity<Map<String, String>> publish(@RequestBody Map<String, Object> payload) {
        String key = UUID.randomUUID().toString();
        producerService.sendMessage(key, payload);
        return ResponseEntity.accepted().body(Map.of(
                "status", "accepted",
                "key", key
        ));
    }

    @PostMapping("/publish/{key}")
    public ResponseEntity<Map<String, String>> publishWithKey(
            @PathVariable String key,
            @RequestBody Map<String, Object> payload) {
        producerService.sendMessage(key, payload);
        return ResponseEntity.accepted().body(Map.of(
                "status", "accepted",
                "key", key
        ));
    }
}
