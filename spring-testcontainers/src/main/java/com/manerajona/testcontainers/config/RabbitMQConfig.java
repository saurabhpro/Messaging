package com.manerajona.testcontainers.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PAYMENT_EVENTS_QUEUE = "paymentEvents";

    private final CachingConnectionFactory connectionFactory;

    public RabbitMQConfig(CachingConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    Queue createUserRegistrationQueue() {
        return new Queue(PAYMENT_EVENTS_QUEUE);
    }

    @Bean
    JacksonJsonMessageConverter converter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(JacksonJsonMessageConverter converter) {
        final var template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}