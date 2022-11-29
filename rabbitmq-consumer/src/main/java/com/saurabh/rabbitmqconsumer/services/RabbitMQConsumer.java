package com.saurabh.rabbitmqconsumer.services;

import com.saurabh.rabbitmqconsumer.exceptions.InvalidSalaryException;
import com.saurabh.rabbitmqconsumer.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RabbitMQConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = "saurabh.queue")
    public void receivedMessage(Employee employee) throws InvalidSalaryException, InterruptedException {
        LOG.info("Received Message From RabbitMQ: {}", employee);
        if (employee.salary() < 0) {
            throw new InvalidSalaryException();
        }

        TimeUnit.SECONDS.sleep(30);
    }
}