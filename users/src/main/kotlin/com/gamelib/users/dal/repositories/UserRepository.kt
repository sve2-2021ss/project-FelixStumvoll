package com.gamelib.users.dal.repositories

import com.gamelib.users.dal.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query("select u from User u where lower(u.name) like lower(concat('%',:name,'%'))")
    fun getAllByNameContaining(name: String): List<User>

    @Query("select u.friends from User u where u.id = :id")
    fun getFriendsOfUser(id: Long): List<User>
}
