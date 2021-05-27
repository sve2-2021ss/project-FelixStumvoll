package com.gamelib.gateway

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GatewayApplication {
    @Bean
    fun circuitBreakerFactory(circuitBreakerRegistry: CircuitBreakerRegistry): ReactiveResilience4JCircuitBreakerFactory =
        ReactiveResilience4JCircuitBreakerFactory().apply {
            configureCircuitBreakerRegistry(circuitBreakerRegistry)
        }
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}
