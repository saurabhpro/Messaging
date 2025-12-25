package com.saurabh.redis.pubsub.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisMessageSubscriber {

    public void onEventMessage(String message) {
        log.info("Received event message: {}", message);
    }

    public void onNotificationMessage(String message) {
        log.info("Received notification message: {}", message);
    }
}
