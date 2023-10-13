package com.saurabh.testcontainers.ports.output.gateway;

import com.saurabh.testcontainers.CardServiceTestContainer;
import com.saurabh.testcontainers.config.WebClientConfig;
import com.saurabh.testcontainers.core.domain.CardDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {CardServiceProxy.class, WebClientConfig.class})
class CardServiceProxyIntegrationTest implements CardServiceTestContainer {

    @Autowired
    CardServiceProxy proxy;

    @BeforeEach
    void check() {
        assertThat(container.isRunning()).isTrue();
    }

    @Test
    void validateCard_test() {
        var isValid = proxy.validateCard(
            new CardDetails("1234567890123456", 1129, 123)
        );
        assertThat(isValid).isTrue();
    }
}