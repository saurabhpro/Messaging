package com.saurabh.rabbitmqconsumer;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqConsumerApplication.class, args);
    }

    @Bean
    public JacksonJsonMessageConverter converter() {
        return new JacksonJsonMessageConverter();
    }
}
