package com.gamelib.games.core.dtos

import com.gamelib.games.dal.entities.Game
import com.gamelib.games.dal.entities.Tag

fun Game.toDto() = GameDto(id!!, title, description)

fun Tag.toDto() = TagDto(id!!, description)