package com.gamelib.search.core.services.search

import kotlinx.coroutines.flow.Flow

interface SearchService {
    fun search(term: String): Flow<Any>
    fun makeRequest(url: String, term: String): Flow<Any>
}