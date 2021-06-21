package com.gamelib.users.api.dtos

import com.gamelib.users.util.NoArg

@NoArg
data class AchievementRegistrationDto(
    val timeAchieved: Long
)