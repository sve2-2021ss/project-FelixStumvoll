package com.gamelib.users.core.services.user

import com.gamelib.users.dal.entities.User

interface UserService {
    fun findAllByName(name: String): List<User>
}