package com.gamelib.games.dal.repositories

import com.gamelib.games.dal.entities.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GameRepository : JpaRepository<Game, Long> {
    @Query(
        """select g from Game g where 
                            lower(g.title) like lower(concat('%',:term,'%')) or 
                            lower(g.description) like lower(concat('%',:term,'%'))"""
    )
    fun getAllByTerm(term: String): List<Game>

    @Query(
        """select distinct g from Tag t 
                            join t.games g where 
                            lower(t.description) like lower(concat('%',:term,'%'))"""
    )
    fun getAllByTagContaining(term: String): List<Game>
}