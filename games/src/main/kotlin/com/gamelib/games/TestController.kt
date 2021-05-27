package com.gamelib.games

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/search")
    fun get(): List<String> {
        print("poggers")
        throw RuntimeException()
        return listOf("pog", "champ")
    }
}