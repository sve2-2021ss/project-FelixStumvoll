package com.gamelib.users.core.services.user

interface UserModificationService : UserService {
    fun addUser(email: String, name: String)
    fun addFriendship(userId: Long, friendId: Long)
    fun removeFriendship(userId: Long, friendId: Long)
}