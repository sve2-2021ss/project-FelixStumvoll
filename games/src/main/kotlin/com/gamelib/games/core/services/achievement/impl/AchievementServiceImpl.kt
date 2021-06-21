package com.gamelib.games.core.services.achievement.impl

import com.gamelib.games.core.exceptions.EntityNotFoundException
import com.gamelib.games.core.services.achievement.AchievementService
import com.gamelib.games.dal.entities.Achievement
import com.gamelib.games.dal.repositories.AchievementRepository
import com.gamelib.games.dal.repositories.GameRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AchievementServiceImpl(
    private val achievementRepository: AchievementRepository,
    private val gameRepository: GameRepository
) : AchievementService {
    @Transactional
    override fun insert(name: String, description: String, gameId: Long) {
        val game = gameRepository.findByIdOrNull(gameId) ?: throw EntityNotFoundException()

        achievementRepository.save(Achievement(name, description, game))
    }

    @Transactional
    override fun update(id: Long, name: String?, description: String?) {
        val achievement =
            achievementRepository.findByIdOrNull(id) ?: throw EntityNotFoundException()

        name?.let {
            achievement.name = it
        }

        description?.let {
            achievement.description = it
        }
    }
}