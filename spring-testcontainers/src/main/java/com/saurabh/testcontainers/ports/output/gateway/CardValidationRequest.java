package com.saurabh.testcontainers.ports.output.gateway;

public record CardValidationRequest(String number, int expDate, int cvc) {
}