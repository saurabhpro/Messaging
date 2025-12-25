package com.saurabh.rabbitmqtestcontainers;

import com.saurabh.rabbitmqtestcontainers.publisher.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;

@Slf4j
@SpringBootTest
@Testcontainers
@ExtendWith(OutputCaptureExtension.class)
public class MessagingApplicationIT {

    @Container
    static GenericContainer<?> rabbit = new GenericContainer<>("rabbitmq:3-management")
            .withExposedPorts(5672, 15672);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", () -> rabbit.getMappedPort(5672));
    }

    @Autowired
    private MessageSender messageSender;

    @Test
    void testBroadcast(CapturedOutput output) {
        log.info("Integration Testing...");
        messageSender.broadcast("Broadcast Test by Saurabh");
        await().atMost(Duration.ofSeconds(5))
                .until(() -> output.getOut().contains("Broadcast Test"), is(true));

        assertThat(output.getOut()).contains("Broadcast Test");
    }
}

/*
 2021-04-13 07:54:18.062  INFO 10292 --- [ntContainer#1-1] c.s.r.consumer.MessageReceiver           : Received topic (#.error) message: an error message
 2021-04-13 07:54:18.062  INFO 10292 --- [ntContainer#0-1] c.s.r.consumer.MessageReceiver           : Received broadcast message: a broadcast message
 2021-04-13 07:54:18.063  INFO 10292 --- [ntContainer#0-1] c.s.r.consumer.MessageReceiver           : Received broadcast message: another broadcast message
 2021-04-13 07:54:18.063  INFO 10292 --- [ntContainer#0-1] c.s.r.consumer.MessageReceiver           : Received broadcast message: that's it
 2021-04-13 07:54:18.416  INFO 10292 --- [           main] c.s.r.MessagingApplicationIT             : Integration Testing...
 2021-04-13 07:54:18.418  INFO 10292 --- [ntContainer#0-1] c.s.r.consumer.MessageReceiver           : Received broadcast message: Broadcast Test by Saurabh
 */