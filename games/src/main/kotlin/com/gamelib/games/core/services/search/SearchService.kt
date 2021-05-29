package com.gamelib.games.core.services.search

import com.gamelib.games.core.dtos.SearchResultDto

interface SearchService {
    fun searchGames(term: String): SearchResultDto
}