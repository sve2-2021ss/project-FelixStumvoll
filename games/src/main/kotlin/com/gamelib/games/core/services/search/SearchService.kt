package com.gamelib.games.core.services.search

import com.gamelib.games.core.dtos.GameDto

interface SearchService {
    fun searchGames(term: String): List<GameDto>
}