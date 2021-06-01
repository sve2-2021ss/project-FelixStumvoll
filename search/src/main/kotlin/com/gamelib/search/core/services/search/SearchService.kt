package com.gamelib.search.core.services.search

import com.gamelib.search.core.services.search.entities.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchService {
    fun search(term: String): Flow<SearchResult>
}