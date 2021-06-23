package com.gamelib.users.dal.repositories

import com.gamelib.users.dal.entities.AchievedAchievement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AchievedAchievementRepository : JpaRepository<AchievedAchievement, Long> {
    @Query("select a from AchievedAchievement a where a.id.userGameInfo.id.user.id = :userId and a.id.userGameInfo.id.gameId = :gameId")
    fun getAchievementsForGameByUser(userId: Long, gameId: Long): List<AchievedAchievement>
}