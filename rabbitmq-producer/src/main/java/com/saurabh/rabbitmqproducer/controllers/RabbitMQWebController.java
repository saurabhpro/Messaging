package com.saurabh.rabbitmqproducer.controllers;

import com.saurabh.rabbitmqproducer.models.Employee;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/saurabh-rabbitmq/v1")
public class RabbitMQWebController {

    private final AmqpTemplate amqpTemplate;

    public RabbitMQWebController(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @PostMapping("/producer")
    public ResponseEntity<String> producer(@RequestBody Employee employee) {
        final Employee emp = new Employee();
        emp.setEmpId(employee.getEmpId());
        emp.setEmpName(employee.getEmpName());
        emp.setSalary(employee.getSalary());

        amqpTemplate.convertAndSend("saurabhExchange", "saurabh", emp);

        return ResponseEntity.ok("Message sent to the RabbitMQ Successfully");
    }
}