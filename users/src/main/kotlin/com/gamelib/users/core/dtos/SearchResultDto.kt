package com.gamelib.users.core.dtos

data class SearchResultDto(val results: List<UserDto>) {
    val type = "users"
}
