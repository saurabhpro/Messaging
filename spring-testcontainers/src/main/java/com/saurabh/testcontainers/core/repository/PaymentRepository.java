package com.saurabh.testcontainers.core.repository;

import com.saurabh.testcontainers.core.domain.Payment;
import com.saurabh.testcontainers.core.domain.PaymentStatus;

import java.util.UUID;

public interface PaymentRepository {
    UUID create(Payment payment, PaymentStatus status);

    boolean updateStatus(UUID id, PaymentStatus status);
}