package com.gamelib.games.dal.entities

import javax.persistence.*

@Entity
data class Tag(
    var description: String,
    @ManyToMany(mappedBy = "tags")
    var games: MutableSet<Game>,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)
