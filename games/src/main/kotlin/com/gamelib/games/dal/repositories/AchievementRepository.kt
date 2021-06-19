package com.gamelib.games.dal.repositories

import com.gamelib.games.dal.entities.Achievement
import org.springframework.data.jpa.repository.JpaRepository


interface AchievementRepository : JpaRepository<Achievement, Long> {
    fun getAchievementByGameId(gameId: Long): List<Achievement>
}