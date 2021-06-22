package com.gamelib.users.core.exceptions

class GameNotOwnedException(val userId: Long, val gameId: Long) :
    RuntimeException("game $userId not owned by user $userId")

class EmailUsedException(val email: String) : RuntimeException()