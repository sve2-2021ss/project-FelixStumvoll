package com.gamelib.games.core.services.tag

import com.gamelib.games.core.dtos.TagDto

interface TagService {
    fun getAllTags(): List<TagDto>
}