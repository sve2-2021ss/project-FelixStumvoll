package com.gamelib.games.dal.entities

import javax.persistence.*

@Entity
data class Game(
    var title: String,
    var description: String,
    @ManyToMany
    var tags: MutableSet<Tag>,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)
