package com.saurabh.rabbitmqtestcontainers.consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
class MessageReceiverTest {

    private MessageReceiver subject;

    @BeforeEach
    public void setUp() {
        this.subject = new MessageReceiver();
    }

    @Test
    void receiveMessageFromFanout(CapturedOutput output) {
        subject.receiveMessageFromTopic("hey there queue");
        assertTrue(output.getAll().contains("hey there queue"));
    }

    @Test
    void receiveMessageFromTopic(CapturedOutput output) {
        subject.receiveMessageFromTopic("hey there topic");
        assertTrue(output.getAll().contains("hey there topic"));
    }
}