package com.gamelib.users.dal.entities

import javax.persistence.*

@Entity
data class User(
    var name: String,
    var email: String,
    @ManyToMany(fetch = FetchType.LAZY)
    var friends: MutableList<User>,
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    var id: Long? = null
)
