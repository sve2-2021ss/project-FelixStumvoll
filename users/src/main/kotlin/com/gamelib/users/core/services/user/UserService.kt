package com.gamelib.users.core.services.user

import com.gamelib.users.dal.entities.User

interface UserService {
    fun getAllByName(name: String): List<User>
    fun getFriendsOfUser(id: Long): List<User>
}