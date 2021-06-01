package com.gamelib.search.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList

fun <T> Flow<T>.suppressError(): Flow<T> = this.catch { }

fun <T, R> Flow<T>.mapAll(func: (List<T>) -> R): Flow<R> = flow {
    this@mapAll
        .catch { throw it }
        .toList()
        .let { emit(func(it)) }
}