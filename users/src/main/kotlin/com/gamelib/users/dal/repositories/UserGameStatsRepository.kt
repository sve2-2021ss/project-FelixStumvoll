package com.gamelib.users.dal.repositories

import com.gamelib.users.dal.entities.UserGameStats
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserGameStatsRepository : JpaRepository<UserGameStats, Long> {
    fun getAllByUserId(userId: Long): List<UserGameStats>

    @Query("select sum(ugs.timePlayedMillis) from UserGameStats ugs where ugs.user.id = :userId")
    fun getTotalPlaytimeForUser(userId: Long): Long

    @Query("select ugs.timePlayedMillis from UserGameStats ugs where ugs.gameId = :gameId and ugs.user.id = :userId")
    fun getPlaytimeForUserByGame(userId: Long, gameId: Long): Long

    @Query("select ugs from UserGameStats ugs where ugs.user.id = :userId and ugs.owned = true")
    fun getGamesOwnedByUser(userId: Long): List<UserGameStats>
}