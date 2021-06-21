package com.gamelib.games.api.controller

import com.gamelib.games.api.dtos.GameInsertDto
import com.gamelib.games.api.dtos.GameUpdateDto
import com.gamelib.games.core.dtos.GameDto
import com.gamelib.games.core.services.game.GameService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class GameController(private val gameService: GameService) {
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): GameDto? = gameService.getById(id)

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated() and hasAuthority('ingest:data')")
    fun updateGame(@PathVariable id: Long, @RequestBody gameUpdateDto: GameUpdateDto) = with(gameUpdateDto) {
        gameService.update(id, title, description, price)
    }

    @GetMapping
    fun getAll(): List<GameDto> = gameService.getAll()

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasAuthority('ingest:data')")
    fun insert(@RequestBody gameInsertDto: GameInsertDto) =
        with(gameInsertDto) { gameService.insert(title, description, price) }

    @GetMapping("/{gameId}/achievement")
    fun getAchievementsForGame(@PathVariable gameId: Long) = gameService.getAchievementsForGame(gameId)
}