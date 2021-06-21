package com.gamelib.users.dal.entities

import com.gamelib.users.util.NoArg
import java.io.Serializable
import javax.persistence.*

@NoArg
data class UserGameInfoId(val gameId: Long, val user: User) : Serializable

@Entity
@IdClass(UserGameInfoId::class)
data class UserGameInfo(
    @Id @JoinColumn @ManyToOne var user: User,
    @Id var gameId: Long,
    var timePlayedMillis: Long,
    var owned: Boolean
)