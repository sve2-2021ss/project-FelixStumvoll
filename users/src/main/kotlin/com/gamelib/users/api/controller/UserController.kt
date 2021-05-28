package com.gamelib.users.api.controller

import com.gamelib.users.core.services.user.UserService
import com.gamelib.users.dal.entities.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.websocket.server.PathParam

@RestController
class UserController(private val userService: UserService) {
    @GetMapping("/findByName")
    fun findAllByName(@RequestParam name: String): List<User> = userService.getAllByName(name)

    @GetMapping("/{id}/friends")
    fun getFriendsOfUser(@PathVariable id: Long): List<User> = userService.getFriendsOfUser(id)
}