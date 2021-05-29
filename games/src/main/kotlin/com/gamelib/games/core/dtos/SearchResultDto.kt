package com.gamelib.games.core.dtos

data class SearchResultDto(val results: List<GameDto>) {
    val type = "games"
}
