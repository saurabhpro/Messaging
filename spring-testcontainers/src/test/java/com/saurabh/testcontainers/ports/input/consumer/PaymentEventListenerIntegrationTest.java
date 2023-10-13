package com.saurabh.testcontainers.ports.input.consumer;

import com.saurabh.testcontainers.CardServiceTestContainer;
import com.saurabh.testcontainers.PostgresTestContainer;
import com.saurabh.testcontainers.RabbitTestContainer;
import com.saurabh.testcontainers.core.domain.CardDetails;
import com.saurabh.testcontainers.core.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.concurrent.Callable;

import static com.saurabh.testcontainers.config.RabbitMQConfig.PAYMENT_EVENTS_QUEUE;
import static com.saurabh.testcontainers.core.domain.PaymentMethod.CARD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class PaymentEventListenerIntegrationTest
    implements CardServiceTestContainer, PostgresTestContainer, RabbitTestContainer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @BeforeEach
    void check() {
        assertThat(CardServiceTestContainer.container.isRunning()).isTrue();
        assertThat(PostgresTestContainer.container.isRunning()).isTrue();
        assertThat(RabbitTestContainer.container.isRunning()).isTrue();
    }

    @Test
    void onPaymentEvent_test(CapturedOutput output) {
        var payment = new Payment(
            BigDecimal.TEN,
            CARD,
            new CardDetails("1234567890123456", 1129, 123)
        );

        rabbitTemplate.convertAndSend(
            PAYMENT_EVENTS_QUEUE,
            new PaymentEvent(payment)
        );

        await().atMost(Duration.ofSeconds(5))
            .until(paymentUpdated(output), is(true));

        assertThat(output.getErr()).isEmpty();
        assertThat(output.getOut()).contains(
            "amount=10",
            "payment_method=CARD",
            "payment_status=PENDING_VALIDATION",
            "payment_status=OK"
        );
    }

    private Callable<Boolean> paymentUpdated(CapturedOutput output) {
        return () -> output.getOut().contains("Payment updated");
    }
}