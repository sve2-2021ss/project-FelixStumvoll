package com.gamelib.games.core.services.game.impl

import com.gamelib.games.core.dtos.GameDto
import com.gamelib.games.core.dtos.TagDto
import com.gamelib.games.core.services.game.GameService
import com.gamelib.games.dal.entities.Game
import com.gamelib.games.dal.entities.Tag
import com.gamelib.games.dal.repositories.GameRepository
import com.gamelib.games.dal.repositories.TagRepository
import org.springframework.stereotype.Service

@Service
class GameServiceImpl(
    private val gameRepository: GameRepository,
    private val tagRepository: TagRepository
) : GameService {
    override fun getById(id: Long): GameDto? = gameRepository.findById(id).map { it.toDto() }.orElse(null)

    override fun getAll(): List<GameDto> = gameRepository.findAll().map { it.toDto() }

    override fun getAllByTerm(term: String): List<GameDto> =
        (gameRepository.getAllByTerm(term) + gameRepository.getAllByTagContaining(term)).map { it.toDto() }

    override fun getTagsForGame(id: Long): List<TagDto> = tagRepository.getAllForGame(id).map { it.toDto() }

    private fun Game.toDto() = GameDto(title, description)

    private fun Tag.toDto() = TagDto(description, id!!)
}