package com.gamelib.users.core.services.user.impl

import com.gamelib.users.core.dtos.UserDto
import com.gamelib.users.core.dtos.UserGameStatsDto
import com.gamelib.users.core.dtos.toDto
import com.gamelib.users.core.exceptions.EntityNotFoundException
import com.gamelib.users.core.exceptions.GameNotOwnedException
import com.gamelib.users.core.services.user.UserModificationService
import com.gamelib.users.dal.entities.User
import com.gamelib.users.dal.entities.UserGameInfo
import com.gamelib.users.dal.repositories.UserGameInfoRepository
import com.gamelib.users.dal.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userGameInfoRepository: UserGameInfoRepository,
) : UserModificationService {
    override fun getById(userId: Long): UserDto? = userRepository.findById(userId).map { it.toDto() }.orElse(null)
    override fun getAll(): List<UserDto> = userRepository.findAll().map { it.toDto() }

    override fun getAllByName(name: String): List<UserDto> =
        userRepository.getAllByNameContaining(name).map { it.toDto() }

    override fun getFriendsOfUser(userId: Long): List<UserDto> =
        userRepository.getFriendsOfUser(userId).map { it.toDto() }

    override fun getOwnedGames(userId: Long): List<UserGameStatsDto> =
        userGameInfoRepository.getGamesOwnedByUser(userId).map { it.toDto(userId) }

    override fun addGameToUser(userId: Long, gameId: Long) {
        val userGameInfo =
            userGameInfoRepository.getByUserIdAndGameId(userId, gameId)

        when {
            userGameInfo == null -> userGameInfoRepository.save(
                UserGameInfo(
                    userRepository.getById(userId),
                    gameId,
                    0,
                    true
                )
            )
            userGameInfo.owned -> return
            else -> userGameInfo.owned = true
        }
    }

    override fun removeGameFromUser(userId: Long, gameId: Long) {
        val userGameInfo =
            userGameInfoRepository.getByUserIdAndGameId(userId, gameId)
                ?: throw EntityNotFoundException()

        if (!userGameInfo.owned) {
            throw GameNotOwnedException(userId, gameId)
        }

        userGameInfo.owned = false
    }

    override fun addUser(email: String, name: String) {
        userRepository.save(User(email, name))
    }

    @Transactional
    override fun addFriendship(userId: Long, friendId: Long) =
        modifyFriendship(userId, friendId, MutableSet<User>::add)

    override fun removeFriendship(userId: Long, friendId: Long) =
        modifyFriendship(userId, friendId, MutableSet<User>::remove)

    private inline fun modifyFriendship(userId: Long, friendId: Long, func: MutableSet<User>.(User) -> Unit) {
        val user = userRepository.findByIdOrNull(userId) ?: throw EntityNotFoundException()
        val friend = userRepository.findByIdOrNull(friendId) ?: throw EntityNotFoundException()
        user.friends.func(friend)
    }
}