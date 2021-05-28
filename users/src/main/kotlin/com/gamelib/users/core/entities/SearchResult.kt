package com.gamelib.users.core.entities

import com.gamelib.users.dal.entities.User

data class SearchResult(val results: List<User>){
    val type = "users"
}
