package com.gamelib.games.api

class ErrorResponse(vararg val errors: String) {
    companion object {
        fun of(errors: List<String>) = ErrorResponse(*errors.toTypedArray())
    }
}