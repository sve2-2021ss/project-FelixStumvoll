package com.gamelib.users.api.controller

import com.gamelib.users.core.services.user.UserService
import com.gamelib.users.dal.entities.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(private val userService: UserService) {
    @GetMapping("/search")
    fun findAllByName(@RequestParam term: String): List<User> =
        listOf(User(1, "Felix", "felix.stumvoll@gmail.com")) //userService.findAllByName(term)
}