package com.saurabh.rabbitmqconsumer.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.IntSequenceGenerator.class,
    property = "@id", 
    scope = Employee.class
)
public record Employee(String empName, String empId, int salary) {

}
