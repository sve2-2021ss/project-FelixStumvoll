package com.urlshortener

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
@SpringBootApplication
@EnableConfigurationProperties(GatewayConfig::class)
class GatewayApplication(val config: GatewayConfig) {
    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator =
        builder.routes()
            .route("stats") {
                it.path("/stats/**")
                    .filters { f -> f.rewritePath("stats/(?<segment>.*)", "/\${segment}") }
                    .uri(config.statsUrl)
            }
            .route("shortener") {
                it.path("/shortener/**")
                    .filters { f -> f.rewritePath("shortener/(?<segment>.*)", "/\${segment}") }
                    .uri(config.shortenerUrl)
            }
            .build()
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}

@ConstructorBinding
@ConfigurationProperties("gateway")
class GatewayConfig(val statsUrl: String, val shortenerUrl: String)