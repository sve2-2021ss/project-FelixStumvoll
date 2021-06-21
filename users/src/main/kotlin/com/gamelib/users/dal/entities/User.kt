package com.gamelib.users.dal.entities

import javax.persistence.*

@Entity
data class User(
    @Column(unique = true)
    var email: String,
    var name: String,
    @ManyToMany(fetch = FetchType.LAZY)
    var friends: MutableSet<User> = mutableSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)
