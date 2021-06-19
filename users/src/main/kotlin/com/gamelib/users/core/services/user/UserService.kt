package com.gamelib.users.core.services.user

import com.gamelib.users.core.dtos.AchievedAchievementDto
import com.gamelib.users.core.dtos.UserDto
import com.gamelib.users.core.dtos.UserGameStatsDto

interface UserService {
    fun getById(userId: Long): UserDto?
    fun getAll(): List<UserDto>
    fun getAllByName(name: String): List<UserDto>
    fun getFriendsOfUser(userId: Long): List<UserDto>
    fun getPlaytimeByGame(userId: Long, gameId: Long): Long
    fun getTotalPlaytime(userId: Long): Long
    fun getOwnedGames(userId: Long): List<UserGameStatsDto>
    fun getAchievementsForGame(userId: Long, gameId: Long): List<AchievedAchievementDto>
}