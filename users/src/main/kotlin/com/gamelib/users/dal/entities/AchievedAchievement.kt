package com.gamelib.users.dal.entities

import com.gamelib.users.util.NoArg
import java.io.Serializable
import javax.persistence.*

@NoArg
data class AchievedAchievementsId(val achievementId: Long, val userGameInfo: UserGameInfo) : Serializable

@Entity
@IdClass(AchievedAchievementsId::class)
data class AchievedAchievement(
    @Id
    @ManyToOne
    @JoinColumns(
        JoinColumn(),
        JoinColumn()
    )
    var userGameInfo: UserGameInfo,
    var timeAchieved: Long,
    @Id
    var achievementId: Long
)
