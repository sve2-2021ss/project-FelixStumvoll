package com.gamelib.search.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.suppressError(): Flow<T> = this.catch { }