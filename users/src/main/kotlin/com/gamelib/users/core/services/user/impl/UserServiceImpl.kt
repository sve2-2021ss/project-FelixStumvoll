package com.gamelib.users.core.services.user.impl

import com.gamelib.users.core.dtos.AchievedAchievementDto
import com.gamelib.users.core.dtos.UserDto
import com.gamelib.users.core.dtos.UserGameStatsDto
import com.gamelib.users.core.dtos.toDto
import com.gamelib.users.core.services.user.UserService
import com.gamelib.users.dal.repositories.AchievedAchievementsRepository
import com.gamelib.users.dal.repositories.UserGameStatsRepository
import com.gamelib.users.dal.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userGameStatsRepository: UserGameStatsRepository,
    private val achievedAchievementsRepository: AchievedAchievementsRepository
) : UserService {
    override fun getById(userId: Long): UserDto? = userRepository.findById(userId).map { it.toDto() }.orElse(null)
    override fun getAll(): List<UserDto> = userRepository.findAll().map { it.toDto() }

    override fun getAllByName(name: String): List<UserDto> =
        userRepository.getAllByNameContaining(name).map { it.toDto() }

    override fun getFriendsOfUser(userId: Long): List<UserDto> =
        userRepository.getFriendsOfUser(userId).map { it.toDto() }

    override fun getPlaytimeByGame(userId: Long, gameId: Long): Long =
        userGameStatsRepository.getPlaytimeForUserByGame(userId, gameId)

    override fun getTotalPlaytime(userId: Long): Long = userGameStatsRepository.getTotalPlaytimeForUser(userId)
    override fun getOwnedGames(userId: Long): List<UserGameStatsDto> =
        userGameStatsRepository.getGamesOwnedByUser(userId).map { it.toDto(userId) }

    override fun getAchievementsForGame(userId: Long, gameId: Long): List<AchievedAchievementDto> =
        achievedAchievementsRepository.getAchievementsForGameByUser(userId, gameId).map { it.toDto(userId, gameId) }
}