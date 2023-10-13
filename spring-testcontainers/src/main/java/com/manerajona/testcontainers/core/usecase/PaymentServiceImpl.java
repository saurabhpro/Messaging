package com.manerajona.testcontainers.core.usecase;

import com.manerajona.testcontainers.core.PaymentService;
import com.manerajona.testcontainers.core.domain.Payment;
import com.manerajona.testcontainers.core.domain.PaymentStatus;
import com.manerajona.testcontainers.core.repository.CardRepository;
import com.manerajona.testcontainers.core.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.manerajona.testcontainers.core.domain.PaymentMethod.CARD;
import static com.manerajona.testcontainers.core.domain.PaymentStatus.ERROR;
import static com.manerajona.testcontainers.core.domain.PaymentStatus.PENDING_VALIDATION;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, CardRepository cardRepository) {
        this.paymentRepository = paymentRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public void registerPayment(Payment payment) {
        if (payment.paymentMethod() == CARD) {
            final var id = paymentRepository.create(payment, PENDING_VALIDATION);
            final var isValidCard = cardRepository.validateCard(payment.card());
            paymentRepository.updateStatus(id, isValidCard ? PaymentStatus.OK : ERROR);
        } else {
            paymentRepository.create(payment, PaymentStatus.OK);
        }
    }
}