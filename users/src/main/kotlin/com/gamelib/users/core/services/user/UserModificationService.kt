package com.gamelib.users.core.services.user

import com.gamelib.users.core.dtos.UserDto

interface UserModificationService : UserService {
    fun addUser(email: String, name: String): UserDto
    fun addFriendship(userId: Long, friendId: Long)
    fun removeFriendship(userId: Long, friendId: Long)
    fun addGameToUser(userId: Long, gameId: Long)
    fun removeGameFromUser(userId: Long, gameId: Long)
}