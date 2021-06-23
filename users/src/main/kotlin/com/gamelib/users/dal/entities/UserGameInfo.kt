package com.gamelib.users.dal.entities

import com.gamelib.users.util.NoArg
import java.io.Serializable
import javax.persistence.*

@NoArg
@Embeddable
data class UserGameInfoId(
    var gameId: Long,
    @JoinColumn @ManyToOne var user: User
) : Serializable

@Entity
data class UserGameInfo(
    @EmbeddedId
    var id: UserGameInfoId,
    var timePlayedMillis: Long,
    var owned: Boolean
)