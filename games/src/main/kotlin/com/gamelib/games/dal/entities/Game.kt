package com.gamelib.games.dal.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Positive

@Entity
data class Game(
    var title: String,
    var description: String,
    @Positive
    var price: Double,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)
