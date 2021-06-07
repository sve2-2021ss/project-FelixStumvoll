package com.gamelib.games.core.services.tag.impl

import com.gamelib.games.core.dtos.TagDto
import com.gamelib.games.core.dtos.toDto
import com.gamelib.games.core.services.tag.TagService
import com.gamelib.games.dal.repositories.TagRepository
import org.springframework.stereotype.Service

@Service
class TagServiceImpl(private val tagRepository: TagRepository) :
    TagService {
    override fun getAllTags(): List<TagDto> = tagRepository.findAll().map { it.toDto() }
}