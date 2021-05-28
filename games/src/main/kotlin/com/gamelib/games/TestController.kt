package com.gamelib.games

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/search")
    fun get(): SearchResult {
//        print("poggers")
//        throw RuntimeException()
        return SearchResult(listOf("pog", "champ"))
    }
}

class SearchResult(val results: List<Any>) {
    val type = "Games"
}