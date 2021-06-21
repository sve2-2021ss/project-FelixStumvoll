package com.gamelib.users.core.dtos

import com.gamelib.users.dal.entities.AchievedAchievement
import com.gamelib.users.dal.entities.User
import com.gamelib.users.dal.entities.UserGameInfo

fun User.toDto() = UserDto(name, email, id!!)

fun UserGameInfo.toDto(userId: Long? = null) =
    UserGameStatsDto(userId ?: user.id!!, gameId, timePlayedMillis, owned)

fun AchievedAchievement.toDto(userId: Long? = null, gameId: Long? = null) =
    AchievedAchievementDto(achievementId, userId ?: userGameInfo.user.id!!, gameId ?: userGameInfo.gameId, timeAchieved)