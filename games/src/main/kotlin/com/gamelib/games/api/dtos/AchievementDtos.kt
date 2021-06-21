package com.gamelib.games.api.dtos

data class AchievementInsertDto(val name: String, val description: String)
data class AchievementUpdateDto(val name: String?, val description: String?)
