package com.gamelib.users.api.controller

import com.gamelib.users.core.dtos.UserDto
import com.gamelib.users.core.services.user.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {
    @GetMapping("/{userId}")
    fun getById(@PathVariable userId: Long): UserDto? = userService.getById(userId)

    @GetMapping
    fun getAll(): List<UserDto> = userService.getAll()

    @GetMapping("/{userId}/friends")
    fun getFriendsOfUser(@PathVariable userId: Long): List<UserDto> = userService.getFriendsOfUser(userId)

    @GetMapping("/{userId}/playtime")
    fun totalPlaytime(@PathVariable userId: Long) = userService.getTotalPlaytime(userId)

    @GetMapping("/{userId}/playtime/{gameId}")
    fun getPlaytimeByGame(@PathVariable userId: Long, @PathVariable gameId: Long) =
        userService.getPlaytimeByGame(userId, gameId)

    @GetMapping("/{userId}/games/owned")
    fun getOwnedGames(@PathVariable userId: Long) = userService.getOwnedGames(userId)

    @GetMapping("/{userId}/games/{gameId}/achievements")
    fun getAchievementsForGame(@PathVariable userId: Long, @PathVariable gameId: Long) =
        userService.getAchievementsForGame(userId, gameId)
}