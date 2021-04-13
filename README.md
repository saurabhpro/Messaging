![build](https://github.com/saurabhpro/Messaging/actions/workflows/maven.yml/badge.svg)


# Messaging
## To start rabbit mq
- https://registry.hub.docker.com/_/rabbitmq/
- run the command 
  ```docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management```

- to confirm
    ```
    ~ % docker ps                                                                  
    CONTAINER ID   IMAGE                   COMMAND                  CREATED              STATUS              PORTS                                                                                                         NAMES
    d97acb6e9297   rabbitmq:3-management   "docker-entrypoint.s…"   About a minute ago   Up About a minute   4369/tcp, 5671/tcp, 0.0.0.0:5672->5672/tcp, 15671/tcp, 15691-15692/tcp, 25672/tcp, 0.0.0.0:15672->15672/tcp   rabbitmq
    ```

## the protocol is The Advanced Message Queuing Protocol (AMQP)
todo

## publishing a message
- we are using springboot Template
- serializer
> NOTE: you have to run the publisher and publish the message FIRST, only then start the consumer application as 
> consumer expects the queue to be present

## curl to publish a message on the queue -> which will end up in DLQ
```curl
curl --location --request POST 'http://localhost:7000/saurabh-rabbitmq/v1/producer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "empName": "Saurabh",
    "empId": "emp111",
    "salary":-50
}'
```

> NOTE: to publish a plain message - change `-50` to `50`

## rabbitmq console
- http://localhost:15672/#/queues