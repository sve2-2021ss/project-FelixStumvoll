# Project Pre-Study

## Project Idea

The initial project idea was to expand the URL-Shortening Service from the first exercise.
After some considerations the project idea was dropped as it doesn't provide with much interesting aspects for a microservice architecture.

The new project idea consists of a game library service like Steam or Epic Games Store. The service manages games, users, the friends of users and which game each user owns.
For the pre-study the service wasn't fully implemented, instead only the relevant parts were implemented to test the covered technologies.

### Architecture

![architecture](doc/images/architecture.png)

The architecture consists of four microservices:

* Gateway: Main entry point for the application
* Games Service: Provides information about games
* Users Service: Provides information about users and what friends they have and games they own
* Search Service: Search Interface which allows searching in multiple other services (games and users in this case)

## Covered Topics

### Kubernetes

Kubernetes was used to manage the deployment of the service. However the services were only deployed in a local `minikube` cluster.
Since Kubernetes deploys pods of docker images the services needed to be built into images.
For this a GitHub Actions pipeline was set up for each service which runs when changes are made to the respective service directory and builds a docker image and pushes it to DockerHub.
Each service defines the configuration files in the `.k8s` directory. These consist of deployment, service, config map.

The databases for the users and games service are also deployed in the cluster using a `StatefulSet`. The configuration files are located in the `.k8s` directory of the respective service.

The deployments also make use of the `Spring Actuator` by using the endpoint as the Kubernetes liveliness probe.

To gain better insights in the K8S cluster the tool `Lens` was used.

![Lens](doc/images/lens.png)

### Spring Gateway

To provide a "single point of entry" to the microservice architecture a gateway was implemented using the Spring Cloud Gateway. At first the configuration was done in code however to allow for greater configurability it was refactored to the spring configuration. In addition to that the Resilience4J CircuitBreaker was setup for the routes of the gateway. The routing defined for the users service can be seen below.

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: users
          uri: ${USERS_SERVICE}
          predicates:
            - Path=/users/**
          filters:
            - RewritePath=/users(?<path>/?.*), $\{path}
            - CircuitBreaker=users
```

### Resilience4J

To provide fault tolerance between communicating services `resilience4j` was chosen as library.
Specifically the modules `CircuitBreaker`, `Retry` and `Timelimiter` were used.
The setup was sadly not as straight forward as initially thought since the library provides lots of different dependencies, namely for Spring Cloud Configuration, Spring Boot Annotations, Project Reactor and one for Kotlin Flow support. In addition to that Spring provides two dependencies as well one for WebMVC and one for spring reactive. However once set up it works very well. The screenshot below shows that the request gets retried and timelimited to 100ms when the users service is not reachable.

![](doc/images/retries.png)

Once the circuit breaker is open the service wont send further requests to the service

![](doc/images/circuitbreaker.png)


### Distributed Tracing

For distributed tracing `Zipkin` was set up inside the Kubernetes cluster using the `openzipkin/zipkin` docker image. The `spring-cloud-starter-sleuth` and `spring-cloud-sleuth-zipkin` dependencies were added to each service.
This allows the tracing of a request through multiple services as one related request.
In the screenshot below the span of a request to the search service can be seen.

![zipkin](doc/images/zipkin.png)

## Remaining Tasks

One of the main task going into the project is to extend the functionality of the services. Since they were only implemented to try out the covered technologies, they are pretty bare bone at the moment.

An additional improvement will be the introduction of helm to better manage the deployment of the services.