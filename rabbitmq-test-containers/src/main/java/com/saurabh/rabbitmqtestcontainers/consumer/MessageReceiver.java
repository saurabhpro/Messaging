package com.saurabh.rabbitmqtestcontainers.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.saurabh.rabbitmqtestcontainers.constants.Constants.BINDING_PATTERN_ERROR;
import static com.saurabh.rabbitmqtestcontainers.constants.Constants.FANOUT_QUEUE_NAME;
import static com.saurabh.rabbitmqtestcontainers.constants.Constants.TOPIC_QUEUE_NAME;

@Slf4j
@Component
public class MessageReceiver {

    @RabbitListener(queues = {FANOUT_QUEUE_NAME})
    public void receiveMessageFromFanout(String message) {
        log.info("Received broadcast message: " + message);
    }


    @RabbitListener(queues = {TOPIC_QUEUE_NAME})
    public void receiveMessageFromTopic(String message) {
        log.info("Received topic (" + BINDING_PATTERN_ERROR + ") message: " + message);
    }

}