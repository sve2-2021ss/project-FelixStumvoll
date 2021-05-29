package com.gamelib.users.api.controller

import com.gamelib.users.core.dtos.UserDto
import com.gamelib.users.core.services.user.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): UserDto? = userService.getById(id)

    @GetMapping
    fun getAll(): List<UserDto> = userService.getAll()

    @GetMapping("/{id}/friends")
    fun getFriendsOfUser(@PathVariable id: Long): List<UserDto> = userService.getFriendsOfUser(id)
}