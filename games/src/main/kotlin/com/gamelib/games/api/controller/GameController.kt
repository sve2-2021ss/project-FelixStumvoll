package com.gamelib.games.api.controller

import com.gamelib.games.core.dtos.GameDto
import com.gamelib.games.core.dtos.TagDto
import com.gamelib.games.core.services.game.GameService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(private val gameService: GameService) {
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): GameDto? = gameService.getById(id)

    @GetMapping
    fun getAll(): List<GameDto> = gameService.getAll()

    @GetMapping("/{id}/tags")
    fun getTagsForGame(@PathVariable id: Long): List<TagDto> = gameService.getTagsForGame(id)
}