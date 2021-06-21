package com.gamelib.games.core.dtos

import com.gamelib.games.dal.entities.Achievement
import com.gamelib.games.dal.entities.Game

fun Game.toDto() = GameDto(id!!, title, description, price)

fun Achievement.toDto(gameId: Long? = null) =
    AchievementDto(name, description, gameId ?: game.id!!, id!!)