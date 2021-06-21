package com.gamelib.games.api.controller

import com.gamelib.games.api.dtos.AchievementInsertDto
import com.gamelib.games.api.dtos.AchievementUpdateDto
import com.gamelib.games.core.services.achievement.AchievementService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@PreAuthorize("isAuthenticated() and hasAuthority('ingest')")
class AchievementController(private val achievementService: AchievementService) {
    @PatchMapping("/achievement/{achievementId}")
    fun updateAchievement(
        @PathVariable achievementId: Long,
        @RequestBody achievementUpdateDto: AchievementUpdateDto
    ) {
        achievementService.update(
            achievementId,
            achievementUpdateDto.name,
            achievementUpdateDto.description
        )
    }

    @PostMapping("/{gameId}/achievement")
    fun addAchievement(@PathVariable gameId: Long, @RequestBody achievementInsertDto: AchievementInsertDto) =
        achievementService.insert(
            achievementInsertDto.name,
            achievementInsertDto.description,
            gameId
        )
}