package com.gamelib.users.core.services.search

import com.gamelib.users.core.entities.SearchResult

interface SearchService {
    fun searchUsers(name: String): SearchResult
}