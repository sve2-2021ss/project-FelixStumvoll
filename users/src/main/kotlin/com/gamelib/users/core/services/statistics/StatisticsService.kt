package com.gamelib.users.core.services.statistics

import com.gamelib.users.core.dtos.AchievedAchievementDto
import javax.validation.constraints.Positive

interface StatisticsService {
    fun getPlaytimeByGame(userId: Long, gameId: Long): Long
    fun getTotalPlaytime(userId: Long): Long
    fun getAchievementsForGame(userId: Long, gameId: Long): List<AchievedAchievementDto>

    fun registerAchievement(
        userId: Long,
        gameId: Long,
        achievementId: Long,
        timeAchieved: Long
    )

    fun registerPlaytime(
        userId: Long,
        gameId: Long,
        @Positive time: Long
    )
}