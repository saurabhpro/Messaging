package com.saurabh.redis.pubsub.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisMessagePublisher publisher;

    @PostMapping("/event")
    public ResponseEntity<Map<String, String>> publishEvent(@RequestBody Map<String, Object> payload) {
        publisher.publishEvent(payload);
        return ResponseEntity.accepted().body(Map.of("status", "published", "channel", "events"));
    }

    @PostMapping("/notification")
    public ResponseEntity<Map<String, String>> publishNotification(@RequestBody Map<String, Object> payload) {
        publisher.publishNotification(payload);
        return ResponseEntity.accepted().body(Map.of("status", "published", "channel", "notifications"));
    }

    @PostMapping("/publish/{channel}")
    public ResponseEntity<Map<String, String>> publish(
        @PathVariable String channel,
        @RequestBody Map<String, Object> payload) {
        publisher.publish(channel, payload);
        return ResponseEntity.accepted().body(Map.of("status", "published", "channel", channel));
    }
}
