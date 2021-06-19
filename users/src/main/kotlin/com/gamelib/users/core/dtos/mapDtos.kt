package com.gamelib.users.core.dtos

import com.gamelib.users.dal.entities.AchievedAchievements
import com.gamelib.users.dal.entities.User
import com.gamelib.users.dal.entities.UserGameStats

fun User.toDto() = UserDto(name, email, id!!)

fun UserGameStats.toDto(userId: Long? = null) =
    UserGameStatsDto(userId ?: user.id!!, gameId, timePlayedMillis, owned)

fun AchievedAchievements.toDto(userId: Long? = null, gameId: Long? = null) =
    AchievedAchievementDto(achievementId, userId ?: userGameStats.user.id!!, gameId ?: userGameStats.gameId)