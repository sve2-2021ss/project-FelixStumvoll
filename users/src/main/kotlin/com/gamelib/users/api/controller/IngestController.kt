package com.gamelib.users.api.controller

import com.gamelib.users.api.dtos.AchievementRegistrationDto
import com.gamelib.users.api.dtos.NewUserDto
import com.gamelib.users.api.dtos.PlaytimeDto
import com.gamelib.users.core.services.statistics.StatisticsService
import com.gamelib.users.core.services.user.UserModificationService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@PreAuthorize("isAuthenticated() and hasAuthority('ingest')")
class IngestController(
    private val statisticsService: StatisticsService,
    private val userService: UserModificationService
) {
    @PostMapping
    fun addUser(@RequestBody newUserDto: NewUserDto) = with(newUserDto) {
        userService.addUser(email, name)
    }

    @PostMapping("{userId}/game/{gameId}")
    fun addGame(@PathVariable userId: Long, @PathVariable gameId: Long) = userService.addGameToUser(userId, gameId)

    @DeleteMapping("{userId}/game/{gameId}")
    fun removeGame(@PathVariable userId: Long, @PathVariable gameId: Long) =
        userService.removeGameFromUser(userId, gameId)

    @PostMapping("{userId}/game/{gameId}/playtime")
    fun addPlaytime(
        @PathVariable userId: Long, @PathVariable gameId: Long, @RequestBody playtimeDto: PlaytimeDto
    ) =
        statisticsService.registerPlaytime(userId, gameId, playtimeDto.time)

    @PostMapping("/{userId}/game/{gameId}/achievement/{achievementId}")
    fun addAchievement(
        @PathVariable userId: Long,
        @PathVariable gameId: Long,
        @PathVariable achievementId: Long,
        @RequestBody achievementDto: AchievementRegistrationDto
    ) = statisticsService.registerAchievement(userId, gameId, achievementId, achievementDto.timeAchieved)

    @PostMapping("/{userId}/friend/{friendId}")
    fun addFriend(@PathVariable userId: Long, @PathVariable friendId: Long) =
        userService.addFriendship(userId, friendId)

    @DeleteMapping("/{userId}/friend/{friendId}")
    fun removeFriend(@PathVariable userId: Long, @PathVariable friendId: Long) =
        userService.addFriendship(userId, friendId)
}