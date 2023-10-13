package com.saurabh.rabbitmqconsumer.models;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

public record Employee(String name, String id, int salary) {

    public Employee {
        checkArgument(nonNull(name), "name is required");
        checkArgument(nonNull(id), "id is required");
    }
}
