package com.gamelib.users.dal.entities

import com.gamelib.users.util.NoArg
import java.io.Serializable
import javax.persistence.*

@NoArg
@Embeddable
data class AchievedAchievementId(
    var achievementId: Long,
    @ManyToOne
    @JoinColumns(
        JoinColumn(),
        JoinColumn()
    )
    var userGameInfo: UserGameInfo
) : Serializable

@Entity
data class AchievedAchievement(
    @EmbeddedId
    var id: AchievedAchievementId,
    var timeAchieved: Long,
)
