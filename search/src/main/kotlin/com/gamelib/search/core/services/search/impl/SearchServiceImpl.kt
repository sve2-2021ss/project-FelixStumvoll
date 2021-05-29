package com.gamelib.search.core.services.search.impl

import com.gamelib.search.SearchConfig
import com.gamelib.search.core.services.search.SearchService
import com.gamelib.search.core.services.search.entities.SearchResult
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.kotlin.circuitbreaker.circuitBreaker
import io.github.resilience4j.kotlin.retry.RetryConfig
import io.github.resilience4j.kotlin.retry.retry
import io.github.resilience4j.retry.Retry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.merge
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlow
import java.net.URI
import java.time.Duration

@Service
class SearchServiceImpl(private val config: SearchConfig) : SearchService {
    val webClient: WebClient = WebClient.create()
    val resilienceMap = config.searchableApis.associateWith {
        Pair(
            Retry.of("search-$it", RetryConfig {
                maxAttempts(3)
            }),
            CircuitBreaker.of("search-$it", CircuitBreakerConfig {
                slidingWindowSize(10)
                minimumNumberOfCalls(5)
                permittedNumberOfCallsInHalfOpenState(3)
                failureRateThreshold(50f)
                waitDurationInOpenState(Duration.ofSeconds(5))
                automaticTransitionFromOpenToHalfOpenEnabled(true)
            })
        )
    }


    @ExperimentalCoroutinesApi
    override fun search(term: String): Flow<SearchResult> = config.searchableApis
        .map { makeRequest(it, term) }
        .merge()

    override fun makeRequest(url: String, term: String): Flow<SearchResult> {
        val (retry, circuitBreaker) = resilienceMap[url]!!

        return webClient
            .get()
            .uri(URI("$url/${config.searchPostfix}?term=$term"))
            .retrieve()
            .bodyToFlow<SearchResult>()
            .circuitBreaker(circuitBreaker)
            .retry(retry)
            .catch {
                println(it)
            }
    }
}