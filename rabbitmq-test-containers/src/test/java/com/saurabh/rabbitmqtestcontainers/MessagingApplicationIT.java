package com.saurabh.rabbitmqtestcontainers;

import com.saurabh.rabbitmqtestcontainers.publisher.MessageSender;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;

/**
 * This is still in Junit4 as it was super difficult to upgrade to 5
 */
@Slf4j
@RunWith(SpringRunner.class) // required for autowiring
@SpringBootTest
@ContextConfiguration(initializers = MessagingApplicationIT.Initializer.class)
public class MessagingApplicationIT {

    @ClassRule
    public static GenericContainer<?> rabbit = new GenericContainer<>("rabbitmq:3-management")
            .withExposedPorts(5672, 15672);

    /**
     * OutputCapture is a JUnit Extension that you can use to capture System.out and System.err output.
     */
    @Rule
    public OutputCaptureRule output = new OutputCaptureRule();

    @Autowired
    private MessageSender messageSender;

    @Test
    public void testBroadcast() {
        log.info("Integration Testing...");
        messageSender.broadcast("Broadcast Test by Saurabh");
        await().atMost(5, TimeUnit.SECONDS).until(isMessageConsumed(), is(true));
    }

    private Callable<Boolean> isMessageConsumed() {
        return () -> output.toString().contains("Broadcast Test");
    }

    public static class Initializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            val values = TestPropertyValues.of(
                    "spring.rabbitmq.host=" + rabbit.getContainerIpAddress(),
                    "spring.rabbitmq.port=" + rabbit.getMappedPort(5672)
            );
            values.applyTo(configurableApplicationContext);
        }
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