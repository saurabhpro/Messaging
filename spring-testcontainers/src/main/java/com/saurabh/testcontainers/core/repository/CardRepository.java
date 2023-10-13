package com.saurabh.testcontainers.core.repository;

import com.saurabh.testcontainers.core.domain.CardDetails;

public interface CardRepository {
    boolean validateCard(CardDetails card);
}