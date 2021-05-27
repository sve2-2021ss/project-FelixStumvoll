package com.gamelib.users.core.services.user.impl

import com.gamelib.users.core.services.user.UserService
import com.gamelib.users.dal.entities.User
import com.gamelib.users.dal.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun findAllByName(name: String): List<User> = userRepository.findAllByName(name)
}