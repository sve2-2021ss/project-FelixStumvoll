package com.gamelib.games.dal.repositories

import com.gamelib.games.dal.entities.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TagRepository : JpaRepository<Tag, Long> {
    @Query("select t from Game g join g.tags t where g.id = :id")
    fun getAllForGame(id: Long): List<Tag>
}