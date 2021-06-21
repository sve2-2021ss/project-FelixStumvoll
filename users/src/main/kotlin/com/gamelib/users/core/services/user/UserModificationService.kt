package com.gamelib.users.core.services.user

interface UserModificationService : UserService {
    fun addUser(email: String, name: String)
    fun addFriendship(userId: Long, friendId: Long)
    fun removeFriendship(userId: Long, friendId: Long)
    fun addGameToUser(userId: Long, gameId: Long)
    fun removeGameFromUser(userId: Long, gameId: Long)
}