package com.saurabh.rabbitmqproducer.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id",
    scope = Employee.class)
public class Employee {

    private String name;
    private String id;
    private int salary;
}