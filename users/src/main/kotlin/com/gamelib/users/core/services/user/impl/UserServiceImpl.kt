package com.gamelib.users.core.services.user.impl

import com.gamelib.users.core.services.user.UserService
import com.gamelib.users.dal.entities.User
import com.gamelib.users.dal.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun getById(id: Long): User? = userRepository.findById(id).orElse(null)

    override fun getAllByName(name: String): List<User> =
        userRepository.getAllByNameContaining(name)

    override fun getFriendsOfUser(id: Long): List<User> =
        userRepository.getFriendsOfUser(id)
}