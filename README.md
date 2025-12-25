![build](https://github.com/saurabhpro/Messaging/actions/workflows/maven.yml/badge.svg)

# Messaging

## Key Technical Concepts

| Technology | Version | Notes |
|------------|---------|-------|
| Java | 25 | LTS release (Sept 2025) |
| Spring Boot | 4.0.1 | Modular starters, Jackson 3 |
| Spring AMQP | 4.x | `JacksonJsonMessageConverter` for Jackson 3 |
| Jackson | 3.x | New package: `tools.jackson.*` |
| Testcontainers | 2.0.3 | JUnit 5 only, new artifact names (`testcontainers-*`) |
| Flyway | 11.x | Requires `spring-boot-starter-flyway` + `flyway-database-postgresql` |
| Gradle | 9.x | Uses `java.toolchain` instead of `sourceCompatibility` |
| JaCoCo | 0.8.13 | Required for Java 25 support |
| Lombok | 1.18.42 | Requires explicit annotation processor config |

### Spring Boot 4.0 Migration Notes
- **Jackson 3**: Use `JacksonJsonMessageConverter` instead of deprecated `Jackson2JsonMessageConverter`
- **Modular starters**: Add `spring-boot-starter-json` explicitly for Jackson support
- **Test annotations moved**: `@DataJpaTest` → `org.springframework.boot.data.jpa.test.autoconfigure`
- **Flyway**: Use `spring-boot-starter-flyway` instead of `flyway-core`

### Testcontainers 2.0 Migration Notes
- **JUnit 4 removed**: Migrate to JUnit 5 with `@Testcontainers` and `@Container`
- **Artifact renaming**: `junit-jupiter` → `testcontainers-junit-jupiter`, `postgresql` → `testcontainers-postgresql`
- **Package changes**: Container classes moved to `org.testcontainers.<module>` (e.g., `org.testcontainers.postgresql.PostgreSQLContainer`)

## Project Structure

| Module | Build | Description | Port |
|--------|-------|-------------|------|
| `rabbitmq-producer` | Maven | REST API to publish messages to RabbitMQ | 7001 |
| `rabbitmq-consumer` | Maven | Consumes messages from RabbitMQ queues | 8083 |
| `rabbitmq-test-containers` | Maven | Integration tests with Testcontainers | - |
| `spring-testcontainers` | Gradle | JPA + RabbitMQ + PostgreSQL with Testcontainers | 8080 |

### Prerequisites
- Java 25 (`sdk install java 25.0.1-tem`)
- Docker (for RabbitMQ and Testcontainers)

### Build & Test
```bash
# Maven projects
mvn -B package --file rabbitmq-producer/pom.xml
mvn -B package --file rabbitmq-consumer/pom.xml
mvn -B package --file rabbitmq-test-containers/pom.xml

# Gradle project
cd spring-testcontainers && ./gradlew build
```

---

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

## The Advanced Message Queuing Protocol (AMQP)
AMQP is an open standard messaging protocol that enables message-oriented middleware to communicate. Key concepts:

- **Exchange**: Receives messages from producers and routes them to queues based on routing rules
- **Queue**: Stores messages until consumed
- **Binding**: Rules that define how messages are routed from exchanges to queues
- **Routing Key**: Attribute used by exchanges to decide how to route messages

### Exchange Types
| Type | Routing Behavior |
|------|------------------|
| Direct | Routes to queues with matching routing key |
| Fanout | Broadcasts to all bound queues (used in this project) |
| Topic | Pattern matching on routing key (e.g., `*.error`, `#.log`) |
| Headers | Routes based on message headers |

This project uses **Fanout** (broadcast) and **Topic** exchanges for message distribution.

## publishing a message
- we are using springboot Template
- serializer
> NOTE: you have to run the publisher and publish the message FIRST, only then start the consumer application as 
> consumer expects the queue to be present

## curl to publish a message on the queue -> which will end up in DLQ
```curl
curl --location 'http://localhost:7001/saurabh-rabbitmq/v1/producer' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Saurabh",
    "id": "emp111",
    "salary":-50
}'
```

> NOTE: to publish a plain message - change `-50` to `50`

## rabbitmq console
- http://localhost:15672/#/queues