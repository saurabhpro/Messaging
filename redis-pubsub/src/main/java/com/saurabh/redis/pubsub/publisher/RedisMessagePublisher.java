package com.saurabh.redis.pubsub.publisher;

import com.saurabh.redis.pubsub.config.RedisConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publishEvent(Object message) {
        log.info("Publishing event: {}", message);
        redisTemplate.convertAndSend(RedisConfig.CHANNEL_EVENTS, message);
    }

    public void publishNotification(Object message) {
        log.info("Publishing notification: {}", message);
        redisTemplate.convertAndSend(RedisConfig.CHANNEL_NOTIFICATIONS, message);
    }

    public void publish(String channel, Object message) {
        log.info("Publishing to channel [{}]: {}", channel, message);
        redisTemplate.convertAndSend(channel, message);
    }
}
