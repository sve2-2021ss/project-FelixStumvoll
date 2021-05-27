package com.gamelib.users.api.controller

import com.gamelib.users.core.services.user.UserService
import com.gamelib.users.dal.entities.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {
    @GetMapping("/findByName")
    fun findAllByName(@RequestParam name: String): List<User> = userService.findAllByName(name)
}