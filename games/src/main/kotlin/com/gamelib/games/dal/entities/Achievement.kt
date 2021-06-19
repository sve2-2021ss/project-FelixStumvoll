package com.gamelib.games.dal.entities

import javax.persistence.*

@Entity
data class Achievement(
    var name: String,
    var description: String,
    @ManyToOne
    @JoinColumn
    var game: Game,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)