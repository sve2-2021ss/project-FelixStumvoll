package com.urlshortener.dal.repositories

import com.urlshortener.dal.entities.ShortUrl
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ShortUrlRepository : CrudRepository<ShortUrl, Long> {
    fun countByShortName(shortName: String): Int
    fun findByShortName(shortName: String): ShortUrl?
    fun findByIdAndUserId(id: Long, userId: Long): ShortUrl?
    fun existsByIdAndUserId(id: Long, userId: Long): Boolean
    fun findAllByUserId(userId: Long): List<ShortUrl>
}