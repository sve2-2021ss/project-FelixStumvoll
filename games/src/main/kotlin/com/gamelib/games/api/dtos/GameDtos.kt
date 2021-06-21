package com.gamelib.games.api.dtos

data class GameInsertDto(
    val title: String,
    val description: String,
    val price: Double
)

data class GameUpdateDto(
    val title: String?,
    val description: String?,
    val price: Double?
)

