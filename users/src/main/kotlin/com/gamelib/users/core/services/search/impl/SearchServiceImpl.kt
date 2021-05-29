package com.gamelib.users.core.services.search.impl

import com.gamelib.users.core.dtos.SearchResultDto
import com.gamelib.users.core.services.search.SearchService
import com.gamelib.users.core.services.user.UserService
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(val userService: UserService) : SearchService {
    override fun searchUsers(term: String): SearchResultDto = SearchResultDto(userService.getAllByName(term))
}