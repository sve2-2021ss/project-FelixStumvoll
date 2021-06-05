package com.gamelib.search.core.services.search.impl

import com.gamelib.search.SearchConfig
import com.gamelib.search.core.services.search.SearchService
import com.gamelib.search.core.services.search.entities.SearchResult
import com.gamelib.search.util.mapAll
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.kotlin.circuitbreaker.circuitBreaker
import io.github.resilience4j.kotlin.retry.RetryConfig
import io.github.resilience4j.kotlin.retry.retry
import io.github.resilience4j.kotlin.timelimiter.TimeLimiterConfig
import io.github.resilience4j.kotlin.timelimiter.timeLimiter
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.timelimiter.TimeLimiter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.merge
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlow
import java.time.Duration

@Service
class SearchServiceImpl(private val config: SearchConfig, private val webClient: WebClient) : SearchService {
    val resilienceMap = config.searchableApis.map { it.name }.associateWith {
        Triple(
            Retry.of("search-$it", RetryConfig {
                maxAttempts(3)
                ignoreExceptions(CallNotPermittedException::class.java)
            }),
            CircuitBreaker.of("search-$it", CircuitBreakerConfig {
                slidingWindowSize(10)
                minimumNumberOfCalls(5)
                permittedNumberOfCallsInHalfOpenState(3)
                waitDurationInOpenState(Duration.ofSeconds(20))
                failureRateThreshold(50f)
                slowCallDurationThreshold(Duration.ofMillis(350))
                slowCallRateThreshold(50f)
                automaticTransitionFromOpenToHalfOpenEnabled(true)
            }),
            TimeLimiter.of(TimeLimiterConfig {
                timeoutDuration(Duration.ofMillis(100))
            })
        )
    }


    @ExperimentalCoroutinesApi
    override fun search(term: String): Flow<SearchResult> = config.searchableApis
        .map { (name, url) -> makeRequest(name, url, term) }
        .merge()

    private fun makeRequest(name: String, url: String, term: String): Flow<SearchResult> {
        val (retry, circuitBreaker, timeLimiter) = resilienceMap[name]!!

        return webClient
            .get()
            .uri("$url/${config.searchPostfix}?term=$term")
            .retrieve()
            .bodyToFlow<Any>()
            .timeLimiter(timeLimiter)
            .circuitBreaker(circuitBreaker)
            .retry(retry)
            .mapAll { SearchResult(name, it) }
            .catch {
                logger.error("Error fetching results from $name @ $url", it)
            }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SearchServiceImpl::class.java)
    }
}