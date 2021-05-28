package com.gamelib.users.dal.repositories

import com.gamelib.users.dal.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun getAllByNameContaining(name: String): List<User>
    @Query("select u.friends from User u where u.id = :id")
    fun getFriendsOfUser(id: Long): List<User>
}
