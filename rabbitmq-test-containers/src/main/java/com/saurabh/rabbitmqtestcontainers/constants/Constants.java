package com.saurabh.rabbitmqtestcontainers.constants;

public final class Constants {
    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate this");
    }

    public static final String FANOUT_QUEUE_NAME = "amqp.fanout.queue";
    public static final String FANOUT_EXCHANGE_NAME = "amqp.fanout.exchange";
    public static final String TOPIC_QUEUE_NAME = "amqp.topic.queue";
    public static final String TOPIC_EXCHANGE_NAME = "amqp.topic.exchange";
    public static final String BINDING_PATTERN_ERROR = "#.error";
}
