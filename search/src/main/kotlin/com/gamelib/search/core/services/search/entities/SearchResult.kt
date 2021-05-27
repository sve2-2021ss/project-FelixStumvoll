package com.gamelib.search.core.services.search.entities

import com.gamelib.search.util.NoArgs

@NoArgs
data class SearchResult(val type: String, val results: List<Any>)
