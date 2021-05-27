package com.gamelib.search.api.controller

import com.gamelib.search.core.services.search.SearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(private val searchService: SearchService) {
    @GetMapping("/search")
    fun search(@RequestParam term: String) = searchService.search(term)
}