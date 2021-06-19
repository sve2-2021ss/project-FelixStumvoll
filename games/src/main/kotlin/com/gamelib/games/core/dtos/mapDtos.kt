package com.gamelib.games.core.dtos

import com.gamelib.games.dal.entities.Achievement
import com.gamelib.games.dal.entities.Game
import com.gamelib.games.dal.entities.Tag

fun Game.toDto() = GameDto(id!!, title, description)

fun Tag.toDto() = TagDto(id!!, description)

fun Achievement.toDto(gameId: Long? = null) =
    AchievementDto(name, description, gameId ?: game.id!!, id!!)