package com.saurabh.testcontainers.core;

import com.saurabh.testcontainers.core.domain.Payment;

public interface PaymentService {
    void registerPayment(Payment payment);
}