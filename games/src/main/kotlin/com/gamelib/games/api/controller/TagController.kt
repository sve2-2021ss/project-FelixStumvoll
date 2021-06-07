package com.gamelib.games.api.controller

import com.gamelib.games.core.dtos.GameDto
import com.gamelib.games.core.dtos.TagDto
import com.gamelib.games.core.services.game.GameService
import com.gamelib.games.core.services.tag.TagService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tags")
class TagController(private val gameService: GameService, private val tagService: TagService) {
    @GetMapping
    fun getAllTags(): List<TagDto> = tagService.getAllTags()

    @GetMapping("/{id}/games")
    fun getGamesForTag(@PathVariable id: Long): List<GameDto> = gameService.getGamesForTag(id)
}