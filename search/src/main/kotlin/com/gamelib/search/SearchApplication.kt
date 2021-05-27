package com.gamelib.search

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(SearchConfig::class)
class SearchApplication

fun main(args: Array<String>) {
    runApplication<SearchApplication>(*args)
}

@ConstructorBinding
@ConfigurationProperties("search")
data class SearchConfig(val searchableApis: List<String>, val searchPostfix: String)