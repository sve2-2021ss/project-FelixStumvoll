package com.gamelib.users.core.services.search.impl

import com.gamelib.users.core.entities.SearchResult
import com.gamelib.users.core.services.search.SearchService
import com.gamelib.users.core.services.user.UserService
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(val userService: UserService) : SearchService {
    override fun searchUsers(name: String): SearchResult = SearchResult(userService.getAllByName(name))
}