package com.gamelib.games.core.services.game

import com.gamelib.games.core.dtos.AchievementDto
import com.gamelib.games.core.dtos.GameDto

interface GameService {
    fun getById(id: Long): GameDto
    fun getAll(): List<GameDto>
    fun insert(
        title: String,
        description: String,
        price: Double
    ): GameDto

    fun update(
        id: Long,
        title: String?,
        description: String?,
        price: Double?
    )

    fun getAllByTerm(term: String): List<GameDto>
    fun getAchievementsForGame(gameId: Long): List<AchievementDto>
}