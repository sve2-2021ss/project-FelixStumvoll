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

To provide a "single point of entry" to the microservice architecture a gateway was implemented using the Spring Cloud Gateway. The configuration was done in the `application.yml` file. In addition to that the Resilience4J CircuitBreaker was setup for the routes of the gateway. The routing defined for the users service can be seen below. This redirects all requests starting with `/users` to the users service and strips the prefix.

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
The setup was sadly not as straight forward as initially thought since the library provides lots of different dependencies, namely for Spring Cloud Configuration, Spring Boot Annotations, Project Reactor and one for Kotlin Flow support. Spring also provides two dependencies as well one for WebMVC and one for Spring reactive. This is in addition to the base dependencies of resilience4j for each module (retry, circuit breaker, etc.)

The gateway uses the reactive spring dependency and the search service uses the Kotlin Flow dependency, as well as the dependencies to the base modules (retry, circuit breaker, timelimiter).

The screenshot below shows a request to the search service. In this case, the users service is down. The screenshot shows, that the request gets retried three times. In addition to that the request is time limited to 100ms before the request is cancelled.

![retries](doc/images/retries.png)

Once enough requests to the users service failed the circuit breaker will open and the search service wont send further requests to the users service.

![circuitbreaker](doc/images/circuitbreaker.png)

To make this work properly the 

### Distributed Tracing

For distributed tracing `Zipkin` was set up inside the Kubernetes cluster using the `openzipkin/zipkin` docker image. The `spring-cloud-starter-sleuth` and `spring-cloud-sleuth-zipkin` dependencies were added to each service.
This allows the tracing of a request through multiple services as one related request.
In the screenshot below the span of a request to the search service can be seen.

![zipkin](doc/images/zipkin.png)

## Remaining Tasks

One of the main task going into the project is to extend the functionality of the services. Since they were only implemented to try out the covered technologies, they are pretty bare bone at the moment.
