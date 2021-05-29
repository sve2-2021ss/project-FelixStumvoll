package com.gamelib.gateway

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.timelimiter.TimeLimiterRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GatewayApplication {
    @Bean
    fun circuitBreakerFactory(
        cbRegistry: CircuitBreakerRegistry,
        tlRegistry: TimeLimiterRegistry
    ): ReactiveResilience4JCircuitBreakerFactory {
        val factory = ReactiveResilience4JCircuitBreakerFactory().apply {
            configureCircuitBreakerRegistry(cbRegistry)
        }

        cbRegistry.allCircuitBreakers.forEach { cb ->
            factory.configure({
                it
                    .timeLimiterConfig(tlRegistry.getConfiguration("default").get())
                    .circuitBreakerConfig(cb.circuitBreakerConfig)
            }, cb.name)
        }

        return factory
    }
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}
