package com.gamelib.users.core.services.user

import com.gamelib.users.core.dtos.UserDto

interface UserService {
    fun getById(id: Long): UserDto?
    fun getAll(): List<UserDto>
    fun getAllByName(name: String): List<UserDto>
    fun getFriendsOfUser(id: Long): List<UserDto>
}