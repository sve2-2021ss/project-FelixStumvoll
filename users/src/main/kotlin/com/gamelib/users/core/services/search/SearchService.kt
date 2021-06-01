package com.gamelib.users.core.services.search

import com.gamelib.users.core.dtos.UserDto

interface SearchService {
    fun searchUsers(term: String): List<UserDto>
}