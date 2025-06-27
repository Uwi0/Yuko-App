package com.kakapo.common.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


inline fun <T, R> Flow<List<T>>.mapEach(crossinline transform: (T) -> R): Flow<List<R>> =
    this.map { list -> list.map(transform) }