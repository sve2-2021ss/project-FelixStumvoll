package com.gamelib.users.dal.repositories

import com.gamelib.users.dal.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findAllByName(name: String): List<User>
}
