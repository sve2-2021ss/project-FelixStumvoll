package com.gamelib.search.core.services.impl

import com.gamelib.search.SearchConfig
import com.gamelib.search.core.services.SearchService
import com.gamelib.search.util.suppressError
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.circuitBreaker
import io.github.resilience4j.kotlin.retry.retry
import io.github.resilience4j.retry.Retry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlow
import java.net.URI

@Service
class SearchServiceImpl(val config: SearchConfig) : SearchService {
    val webClient: WebClient = WebClient.create()
    val retry = Retry.ofDefaults("search")
    val circuitBreaker = CircuitBreaker.ofDefaults("search")

    @ExperimentalCoroutinesApi
    override fun search(term: String): Flow<Any> = config.searchableApis
        .map { makeRequest(it, term) }
        .merge()
        .suppressError()

    override fun makeRequest(url: String, term: String): Flow<Any> = webClient
        .get()
        .uri(URI("$url/${config.searchPostfix}?term=$term"))
        .retrieve()
        .bodyToFlow<Any>()
        .retry(retry)
        .circuitBreaker(circuitBreaker)
        .onEach { println("test") }
}