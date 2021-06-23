package com.gamelib.users.api.controller

import com.gamelib.users.core.services.statistics.StatisticsService
import com.gamelib.users.core.services.user.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService, private val statisticsService: StatisticsService) {
    @GetMapping("/{userId}")
    fun getById(@PathVariable userId: Long) = userService.getById(userId)

    @GetMapping
    fun getAll() = userService.getAll()

    @GetMapping("/{userId}/friend")
    fun getFriendsOfUser(@PathVariable userId: Long) = userService.getFriendsOfUser(userId)

    @GetMapping("/{userId}/playtime")
    fun totalPlaytime(@PathVariable userId: Long) = statisticsService.getTotalPlaytime(userId)

    @GetMapping("/{userId}/game/{gameId}/playtime")
    fun getPlaytimeByGame(@PathVariable userId: Long, @PathVariable gameId: Long) =
        statisticsService.getPlaytimeByGame(userId, gameId)

    @GetMapping("/{userId}/game/owned")
    fun getOwnedGames(@PathVariable userId: Long) = userService.getOwnedGames(userId)

    @GetMapping("/{userId}/game/{gameId}/achievement")
    fun getAchievementsForGame(@PathVariable userId: Long, @PathVariable gameId: Long) =
        statisticsService.getAchievementsForGame(userId, gameId)
}