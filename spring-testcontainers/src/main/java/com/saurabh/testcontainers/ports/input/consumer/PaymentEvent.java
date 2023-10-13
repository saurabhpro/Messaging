package com.saurabh.testcontainers.ports.input.consumer;

import com.saurabh.testcontainers.core.domain.Payment;

public record PaymentEvent(Payment payment) {
}