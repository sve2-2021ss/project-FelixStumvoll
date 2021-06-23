package com.gamelib.games.core.services.game.impl

import com.gamelib.games.core.dtos.AchievementDto
import com.gamelib.games.core.dtos.GameDto
import com.gamelib.games.core.dtos.toDto
import com.gamelib.games.core.services.game.GameService
import com.gamelib.games.dal.entities.Game
import com.gamelib.games.dal.repositories.AchievementRepository
import com.gamelib.games.dal.repositories.GameRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
class GameServiceImpl(
    private val gameRepository: GameRepository,
    private val achievementRepository: AchievementRepository
) : GameService {
    override fun getById(id: Long): GameDto =
        gameRepository.findByIdOrNull(id)?.toDto() ?: throw EntityNotFoundException()

    override fun getAll(): List<GameDto> = gameRepository.findAll().map { it.toDto() }

    @Transactional
    override fun insert(title: String, description: String, price: Double): GameDto =
        gameRepository.save(Game(title, description, price)).toDto()

    @Transactional
    override fun update(id: Long, title: String?, description: String?, price: Double?) {
        val game = gameRepository.findByIdOrNull(id) ?: throw EntityNotFoundException()

        title?.let {
            game.title = it
        }

        description?.let {
            game.description = it
        }

        price?.let {
            game.price = it
        }
    }

    override fun getAllByTerm(term: String): List<GameDto> =
        gameRepository.getAllByTerm(term).map { it.toDto() }

    override fun getAchievementsForGame(gameId: Long): List<AchievementDto> =
        achievementRepository.getAchievementByGameId(gameId).map { it.toDto(gameId) }
}