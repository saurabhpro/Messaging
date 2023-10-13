package com.saurabh.rabbitmqconsumer.exceptions;

public class InvalidSalaryException extends RuntimeException {

    public InvalidSalaryException(int salary) {
        super("Expected salary to be >= 0, but found %s".formatted(salary));
    }
}