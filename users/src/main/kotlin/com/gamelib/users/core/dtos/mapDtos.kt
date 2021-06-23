package com.gamelib.users.core.dtos

import com.gamelib.users.dal.entities.AchievedAchievement
import com.gamelib.users.dal.entities.User
import com.gamelib.users.dal.entities.UserGameInfo

fun User.toDto() = UserDto(name, email, id!!)

fun UserGameInfo.toDto(userId: Long? = null) =
    UserGameStatsDto(userId ?: id.user.id!!, id.gameId, timePlayedMillis, owned)

fun AchievedAchievement.toDto(userId: Long? = null, gameId: Long? = null) =
    AchievedAchievementDto(
        id.achievementId,
        userId ?: id.userGameInfo.id.user.id!!,
        gameId ?: id.userGameInfo.id.gameId,
        timeAchieved
    )