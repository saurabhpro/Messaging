package com.saurabh.testcontainers.ports.input.consumer;

import com.saurabh.testcontainers.core.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.saurabh.testcontainers.config.RabbitMQConfig.PAYMENT_EVENTS_QUEUE;

@Component
public class PaymentEventListener {

    private static final Logger log = LoggerFactory.getLogger(PaymentEventListener.class);

    private final PaymentService paymentService;

    public PaymentEventListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = {PAYMENT_EVENTS_QUEUE})
    public void onPaymentEvent(PaymentEvent event) {
        try {
            paymentService.registerPayment(event.payment());
        } catch (Exception e) {
            log.warn("There was an error on event {}", event);
            log.error(e.getMessage(), e);
        }
    }
}