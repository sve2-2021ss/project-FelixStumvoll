package com.gamelib.users.dal.repositories

import com.gamelib.users.dal.entities.UserGameInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserGameInfoRepository : JpaRepository<UserGameInfo, Long> {
    @Query("select sum(ugi.timePlayedMillis) from UserGameInfo ugi where ugi.id.user.id = :userId")
    fun getTotalPlaytimeForUser(userId: Long): Long

    @Query("select ugi.timePlayedMillis from UserGameInfo ugi where ugi.id.gameId = :gameId and ugi.id.user.id = :userId")
    fun getPlaytimeForUserByGame(userId: Long, gameId: Long): Long

    @Query("select ugi from UserGameInfo ugi where ugi.id.user.id = :userId and ugi.owned = true")
    fun getGamesOwnedByUser(userId: Long): List<UserGameInfo>

    @Query("select ugi from UserGameInfo ugi where ugi.id.user.id = :userId and ugi.id.gameId = :gameId")
    fun getByUserIdAndGameId(userId: Long, gameId: Long): UserGameInfo?
}