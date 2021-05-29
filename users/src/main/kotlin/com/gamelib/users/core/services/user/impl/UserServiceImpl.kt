package com.gamelib.users.core.services.user.impl

import com.gamelib.users.core.dtos.UserDto
import com.gamelib.users.core.services.user.UserService
import com.gamelib.users.dal.entities.User
import com.gamelib.users.dal.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun getById(id: Long): UserDto? = userRepository.findById(id).map { it.toDto() }.orElse(null)
    override fun getAll(): List<UserDto> = userRepository.findAll().map { it.toDto() }

    override fun getAllByName(name: String): List<UserDto> =
        userRepository.getAllByNameContaining(name).map { it.toDto() }

    override fun getFriendsOfUser(id: Long): List<UserDto> =
        userRepository.getFriendsOfUser(id).map { it.toDto() }

    private fun User.toDto(): UserDto = UserDto(name, email, id!!)
}