package com.gamelib.users.core.services.statistics.impl

import com.gamelib.users.core.dtos.AchievedAchievementDto
import com.gamelib.users.core.dtos.toDto
import com.gamelib.users.core.exceptions.GameNotOwnedException
import com.gamelib.users.core.services.statistics.StatisticsService
import com.gamelib.users.dal.entities.AchievedAchievement
import com.gamelib.users.dal.repositories.AchievedAchievementRepository
import com.gamelib.users.dal.repositories.UserGameInfoRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional
import javax.validation.constraints.Positive

@Service
class StatisticsServiceImpl(
    private val userGameInfoRepository: UserGameInfoRepository,
    private val achievedAchievementRepository: AchievedAchievementRepository
) : StatisticsService {
    override fun getPlaytimeByGame(userId: Long, gameId: Long): Long =
        userGameInfoRepository.getPlaytimeForUserByGame(userId, gameId)

    override fun getTotalPlaytime(userId: Long): Long = userGameInfoRepository.getTotalPlaytimeForUser(userId)

    override fun getAchievementsForGame(userId: Long, gameId: Long): List<AchievedAchievementDto> =
        achievedAchievementRepository.getAchievementsForGameByUser(userId, gameId).map { it.toDto(userId, gameId) }

    @Transactional
    override fun registerAchievement(userId: Long, gameId: Long, achievementId: Long, timeAchieved: Long) {
        val userGameInfo =
            userGameInfoRepository.getByUserIdAndGameId(userId, gameId)
                ?: throw EntityNotFoundException()

        if (!userGameInfo.owned) throw GameNotOwnedException(userId, gameId)

        achievedAchievementRepository.save(
            AchievedAchievement(
                userGameInfo,
                timeAchieved,
                achievementId
            )
        )
    }

    @Transactional
    override fun registerPlaytime(userId: Long, gameId: Long, @Positive time: Long) {
        val userGameInfo =
            userGameInfoRepository.getByUserIdAndGameId(userId, gameId)
                ?: throw EntityNotFoundException()

        if (!userGameInfo.owned) throw GameNotOwnedException(userId, gameId)

        userGameInfo.timePlayedMillis += time
    }
}