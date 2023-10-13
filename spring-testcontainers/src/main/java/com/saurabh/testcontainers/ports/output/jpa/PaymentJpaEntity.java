package com.saurabh.testcontainers.ports.output.jpa;

import com.saurabh.testcontainers.core.domain.PaymentMethod;
import com.saurabh.testcontainers.core.domain.PaymentStatus;
import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.AccessType.FIELD;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(schema = "payment_service", name = "payment")
@Access(FIELD)
public class PaymentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal amount;
    @Enumerated(STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(STRING)
    private PaymentStatus paymentStatus;
    @CreationTimestamp
    private Instant paymentDate;

    public PaymentJpaEntity(BigDecimal amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public PaymentJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (PaymentJpaEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "payment(" +
            "id=" + id +
            ", amount=" + amount +
            ", payment_method=" + paymentMethod +
            ", payment_status=" + paymentStatus +
            ", payment_date=" + paymentDate +
            ')';
    }
}