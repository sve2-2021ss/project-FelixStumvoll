package com.gamelib.users.core.services.search

import com.gamelib.users.core.dtos.SearchResultDto

interface SearchService {
    fun searchUsers(term: String): SearchResultDto
}