package com.gamelib.users.dal.entities

import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["email"], name = "emailUniqueConstraint")])
data class User(
    var email: String,
    var name: String,
    @ManyToMany(fetch = FetchType.LAZY)
    var friends: MutableSet<User> = mutableSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)
