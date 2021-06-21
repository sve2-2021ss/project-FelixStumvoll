package com.gamelib.games.core.services.achievement

interface AchievementService {
    fun insert(name: String, description: String, gameId: Long)
    fun update(id: Long, name: String?, description: String?)
}