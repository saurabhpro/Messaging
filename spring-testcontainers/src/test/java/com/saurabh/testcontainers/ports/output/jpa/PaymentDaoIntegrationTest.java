package com.saurabh.testcontainers.ports.output.jpa;

import com.saurabh.testcontainers.PostgresTestContainer;
import com.saurabh.testcontainers.core.domain.Payment;
import com.saurabh.testcontainers.core.domain.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import java.math.BigDecimal;
import java.util.UUID;

import static com.saurabh.testcontainers.core.domain.PaymentMethod.CARD;
import static com.saurabh.testcontainers.core.domain.PaymentStatus.PENDING_VALIDATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@ComponentScan(basePackages = {"com.saurabh.testcontainers.ports.output.jpa"})
@AutoConfigureTestDatabase(replace = NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentDaoIntegrationTest implements PostgresTestContainer {

    static UUID paymentId;

    @Autowired
    PaymentDao dao;

    @BeforeEach
    void check() {
        assertThat(container.isRunning()).isTrue();
    }

    @Test
    @Order(1)
    @Commit
    void create_test() {
        paymentId = dao.create(
            new Payment(BigDecimal.TEN, CARD),
            PENDING_VALIDATION
        );

        assertThat(paymentId).isNotNull();
    }

    @Test
    @Order(2)
    void updateStatus_test_withExistingPaymentId() {
        assertThat(dao.updateStatus(paymentId, PaymentStatus.OK)).isTrue();
    }

    @Test
    @Order(3)
    void updateStatus_test_withNonExistingPaymentId() {
        var randomPaymentId = UUID.randomUUID();
        assertThat(dao.updateStatus(randomPaymentId, PaymentStatus.OK)).isFalse();
    }
}