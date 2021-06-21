package com.gamelib.users.core.exceptions

class EntityNotFoundException : RuntimeException()

class GameNotOwnedException(val userId: Long, val gameId: Long) :
    RuntimeException("game $userId not owned by user $userId")