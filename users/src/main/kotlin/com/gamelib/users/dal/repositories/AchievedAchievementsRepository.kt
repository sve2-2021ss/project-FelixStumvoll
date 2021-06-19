package com.gamelib.users.dal.repositories

import com.gamelib.users.dal.entities.AchievedAchievements
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AchievedAchievementsRepository : JpaRepository<AchievedAchievements, Long> {
    @Query("select a from AchievedAchievements a where a.userGameStats.user.id = :userId and a.userGameStats.gameId = :gameId")
    fun getAchievementsForGameByUser(userId: Long, gameId: Long): List<AchievedAchievements>
}