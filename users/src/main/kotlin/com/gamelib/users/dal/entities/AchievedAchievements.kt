package com.gamelib.users.dal.entities

import com.gamelib.users.util.NoArg
import java.io.Serializable
import javax.persistence.*

@NoArg
data class AchievedAchievementsId(val achievementId: Long, val userGameStats: UserGameStats) : Serializable

@Entity
@IdClass(AchievedAchievementsId::class)
data class AchievedAchievements(
    @Id
    @ManyToOne
    @JoinColumns(
        JoinColumn(),
        JoinColumn()
    )
    var userGameStats: UserGameStats,
    var timeAchieved: Long,
    @Id
    var achievementId: Long
)
