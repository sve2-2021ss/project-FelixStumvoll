package com.gamelib.games.api.controller

import com.gamelib.games.core.services.search.SearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(private val searchService: SearchService) {
    @GetMapping("/search")
    fun findAllByName(@RequestParam(required = true) term: String) = searchService.searchGames(term)
}