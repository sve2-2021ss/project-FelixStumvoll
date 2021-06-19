package com.gamelib.users.core.dtos

data class UserGameStatsDto(
    val userId: Long,
    val gameId: Long,
    val timePlayedMillis: Long,
    val owned: Boolean
)
