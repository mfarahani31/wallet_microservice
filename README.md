# WALLET MICROSERVICE

A simple wallet microservice running on the JVM that manages credit/debit transactions on behalf of players.

---
A monetary account holds the current balance for a player. The balance can be modified by registering transactions on
the account, either `debit`
transactions (removing funds) or `credit` transactions (adding funds).

---
I used **`Spring-boot`** as a main tool to develop code. It's obviously one of the most common frameworks to produce
more efficient and shorter codes. It makes me to focus on business and the logic of project.

Here are a few advantages of Spring-boot;

Spring Boot accelerates software development by providing an out-of-the-box set of conventions, abstractions, and
mechanisms.

Concretely, Spring Boot comes in the form of a parent POM and dependencies -- aka "starters" -- (Maven or Gradle). And
for example I dded Lombok library, to avoid writing BoilerPlate code.

This project follows the `RESTFUL` best practices exposing needed endpoints

---

The most challenging item was avoiding concurrency errors. Because it was very delicious for me to research and study
concurrency and found `Versioning` of entities in the project.

I hope this project will be continued in order to implement other concepts such as transfer

---

** Getting started**
---
Retrieve Sources

    https://github.com/mfarahani31/wallet_microservice.git

Launch the application

    $ mvn spring-boot:run

### APIs

Method | Path           | Description                    |
-------|----------------|--------------------------------|
GET    | /api/v1/transactions/byUserId/{userId}      | retrieve all transactions for a user|
POST    | /api/v1/transactions/credit/{userId} | save a credit transaction for a user|
POST   | /api/v1/transactions/debit/{userId}   | save a debit transaction for a user |
Get    | /api/v1/currentBalance/{userId}      | retrieve the current balance of a user|

** You can see the full document of APIs on "[http://localhost:8080/swagger-ui/]()"

Please check it out !

** And also you can see the database tables on "[http://localhost:8080/h2-console]()"

---

The tools that were used in this project;

- Java v11
- Spring-boot v2.7.3
- Spring-boot validation ; for validation
- Spring-boot data jpa ; for persistence layer
- Spring-boot dev tools
- H2 database
- Lombok
- Spring-boot test; for write unit test and integration test (`With coverage of 100%`)
- Swagger for; documenting APIs
- Mapstruct; for mapping DTOs
- Flyway; for database versioning
- Docker; for containerization

---
Notice!!!
--
I should say that; It could have also **Actuator** as a monitoring and availability tool, **RabbitMQ** or **Kafka** as a
message broker, **Spring-security** as an authentication and user management tool, and some other tools like **
Kubernetes** and **Jenkins** but it was unnecessary and I wanted to keep it simple so I didn't add them and ignored them
for this step. I know how and when to use these tools!

---
---
With remember of Mahsa Amini and Nika Shakarami 