package com.gamelib.users.dal.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var name: String,
    var email: String
)
