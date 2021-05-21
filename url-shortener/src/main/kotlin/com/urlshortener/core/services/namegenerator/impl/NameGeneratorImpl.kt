package com.urlshortener.core.services.namegenerator.impl

import com.urlshortener.core.services.namegenerator.NameGenerator
import org.springframework.stereotype.Service

@Service
class NameGeneratorImpl : NameGenerator {
    override fun generateName(length: Int): String =
        (1..length)
            .map { charPool.random() }
            .joinToString("")

    companion object {
        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    }
}