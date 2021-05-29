package com.gamelib.users.api.controller

import com.gamelib.users.core.services.user.UserService
import com.gamelib.users.dal.entities.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): User? = userService.getById(id)

    @GetMapping("/{id}/friends")
    fun getFriendsOfUser(@PathVariable id: Long): List<User> = userService.getFriendsOfUser(id)
}