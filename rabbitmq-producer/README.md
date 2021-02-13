## To start rabbit mq 
- https://registry.hub.docker.com/_/rabbitmq/
- run the command ```docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management```

## the protocol is The Advanced Message Queuing Protocol (AMQP)
todo

## publishing a message
- we are using springboot Template
- serializer

## curl to publish a message on the queue
```curl
curl --location --request POST 'http://localhost:7000/saurabh-rabbitmq/v1/producer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "empName": "Saurabh",
    "empId": "emp111",
    "salary":-50
}'
```
## rabbitmq console
- http://localhost:15672/#/queues
