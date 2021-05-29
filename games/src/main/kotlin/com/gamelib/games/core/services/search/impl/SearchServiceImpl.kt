package com.gamelib.games.core.services.search.impl

import com.gamelib.games.core.dtos.SearchResultDto
import com.gamelib.games.core.services.game.GameService
import com.gamelib.games.core.services.search.SearchService
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(private val gameService: GameService) : SearchService {
    override fun searchGames(term: String): SearchResultDto = SearchResultDto(gameService.getAllByTerm(term))
}