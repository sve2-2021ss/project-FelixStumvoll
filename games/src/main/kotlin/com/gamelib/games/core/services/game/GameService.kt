package com.gamelib.games.core.services.game

import com.gamelib.games.core.dtos.GameDto
import com.gamelib.games.core.dtos.TagDto

interface GameService {
    fun getById(id: Long): GameDto?
    fun getAll(): List<GameDto>
    fun getAllByTerm(term: String): List<GameDto>
    fun getTagsForGame(id: Long): List<TagDto>
}