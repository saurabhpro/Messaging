package com.saurabh.rabbitmqtestcontainers.publisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.saurabh.rabbitmqtestcontainers.constants.Constants.FANOUT_EXCHANGE_NAME;
import static com.saurabh.rabbitmqtestcontainers.constants.Constants.TOPIC_EXCHANGE_NAME;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Basics: Unit tests for sender and receiver
 * <p>
 * Unit tests for components that are responsible for sending/receiving messages to/from RabbitMQ queues.
 * <p>
 * I don’t know why, maybe they are confused by the “complexity” of this scenario,
 * but unit tests for this should test only the units you implemented.
 * Everything that comes from the framework can be mocked at this stage.
 */
class MessageSenderTest {

    private MessageSender subject;
    private RabbitTemplate rabbitTemplateMock;

    @BeforeEach
    public void setUp() {
        this.rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
        this.subject = new MessageSender(this.rabbitTemplateMock);
    }

    @Test
    void testBroadcast() {
        assertThatCode(() -> this.subject.broadcast("Test")).doesNotThrowAnyException();

        Mockito.verify(this.rabbitTemplateMock)
                .convertAndSend(eq(FANOUT_EXCHANGE_NAME), eq(""), eq("Test"));
    }

    @Test
    void testSendError() {
        assertThatCode(() -> this.subject.sendError("Test Error")).doesNotThrowAnyException();

        Mockito.verify(this.rabbitTemplateMock)
                .convertAndSend(eq(TOPIC_EXCHANGE_NAME), endsWith("error"), eq("Test Error"));
    }
}