package com.saurabh.rabbitmqtestcontainers.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.saurabh.rabbitmqtestcontainers.constants.Constants.BINDING_PATTERN_ERROR;
import static com.saurabh.rabbitmqtestcontainers.constants.Constants.FANOUT_EXCHANGE_NAME;
import static com.saurabh.rabbitmqtestcontainers.constants.Constants.FANOUT_QUEUE_NAME;
import static com.saurabh.rabbitmqtestcontainers.constants.Constants.TOPIC_EXCHANGE_NAME;
import static com.saurabh.rabbitmqtestcontainers.constants.Constants.TOPIC_QUEUE_NAME;

@Configuration
public class MessagingConfig {

    private static final boolean NON_DURABLE = false;
    private static final boolean DO_NOT_AUTO_DELETE = false;

    @Bean
    public Declarables topicBindings() {
        final var topicQueue = new Queue(TOPIC_QUEUE_NAME, NON_DURABLE);

        final var topicExchange = new TopicExchange(
            TOPIC_EXCHANGE_NAME,
            NON_DURABLE,
            DO_NOT_AUTO_DELETE);

        return new Declarables(topicQueue, topicExchange, BindingBuilder
            .bind(topicQueue)
            .to(topicExchange)
            .with(BINDING_PATTERN_ERROR));
    }

    @Bean
    public Declarables fanoutBindings() {
        final var fanoutQueue = new Queue(FANOUT_QUEUE_NAME, NON_DURABLE);

        final var fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE_NAME, NON_DURABLE,
            DO_NOT_AUTO_DELETE);

        return new Declarables(fanoutQueue, fanoutExchange, BindingBuilder
            .bind(fanoutQueue)
            .to(fanoutExchange));
    }

}