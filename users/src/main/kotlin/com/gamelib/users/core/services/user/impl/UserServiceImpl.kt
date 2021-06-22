package com.gamelib.users.core.services.user.impl

import com.gamelib.users.core.dtos.UserDto
import com.gamelib.users.core.dtos.UserGameStatsDto
import com.gamelib.users.core.dtos.toDto
import com.gamelib.users.core.exceptions.EmailUsedException
import com.gamelib.users.core.exceptions.GameNotOwnedException
import com.gamelib.users.core.services.user.UserModificationService
import com.gamelib.users.dal.entities.User
import com.gamelib.users.dal.entities.UserGameInfo
import com.gamelib.users.dal.repositories.UserGameInfoRepository
import com.gamelib.users.dal.repositories.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userGameInfoRepository: UserGameInfoRepository,
    private val transactionTemplate: TransactionTemplate
) : UserModificationService {
    override fun getById(userId: Long): UserDto =
        userRepository.findByIdOrNull(userId)?.toDto() ?: throw EntityNotFoundException()

    override fun getAll(): List<UserDto> = userRepository.findAll().map { it.toDto() }

    override fun getAllByName(name: String): List<UserDto> =
        userRepository.getAllByNameContaining(name).map { it.toDto() }

    override fun getFriendsOfUser(userId: Long): List<UserDto> =
        userRepository.getFriendsOfUser(userId).map { it.toDto() }

    override fun getOwnedGames(userId: Long): List<UserGameStatsDto> =
        userGameInfoRepository.getGamesOwnedByUser(userId).map { it.toDto(userId) }

    @Transactional
    override fun addGameToUser(userId: Long, gameId: Long) {
        val user = userRepository.findByIdOrNull(userId) ?: throw EntityNotFoundException()

        val userGameInfo =
            userGameInfoRepository.getByUserIdAndGameId(userId, gameId)

        when {
            userGameInfo == null -> userGameInfoRepository.save(
                UserGameInfo(
                    user,
                    gameId,
                    0,
                    true
                )
            )
            userGameInfo.owned -> return
            else -> userGameInfo.owned = true
        }
    }

    @Transactional
    override fun removeGameFromUser(userId: Long, gameId: Long) {
        val userGameInfo =
            userGameInfoRepository.getByUserIdAndGameId(userId, gameId)
                ?: throw EntityNotFoundException()

        if (!userGameInfo.owned) throw GameNotOwnedException(userId, gameId)

        userGameInfo.owned = false
    }

    override fun addUser(email: String, name: String) {
        try {
            transactionTemplate.execute { userRepository.save(User(email, name)) }
        } catch (ex: DataIntegrityViolationException) {
            throw EmailUsedException(email)
        }
    }

    @Transactional
    override fun addFriendship(userId: Long, friendId: Long) =
        modifyFriendship(userId, friendId, MutableSet<User>::add)

    @Transactional
    override fun removeFriendship(userId: Long, friendId: Long) =
        modifyFriendship(userId, friendId, MutableSet<User>::remove)

    private inline fun modifyFriendship(userId: Long, friendId: Long, func: MutableSet<User>.(User) -> Unit) {
        val user = userRepository.findByIdOrNull(userId) ?: throw EntityNotFoundException()
        val friend = userRepository.findByIdOrNull(friendId) ?: throw EntityNotFoundException()
        user.friends.func(friend)
    }
}