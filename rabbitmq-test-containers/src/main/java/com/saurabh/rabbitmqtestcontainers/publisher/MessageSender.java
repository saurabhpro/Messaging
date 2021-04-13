package com.saurabh.rabbitmqtestcontainers.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.saurabh.rabbitmqtestcontainers.constants.Constants.FANOUT_EXCHANGE_NAME;
import static com.saurabh.rabbitmqtestcontainers.constants.Constants.TOPIC_EXCHANGE_NAME;

@Component
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void broadcast(String message) {
        this.rabbitTemplate.convertAndSend(FANOUT_EXCHANGE_NAME, "", message);
    }

    public void sendError(String message) {
        this.rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "this.is.an.error", message);
    }
}