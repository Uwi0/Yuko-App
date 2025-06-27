package org.kakapo.project.presentation.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface CustomResult<out T> {
    data class Success<T>(val data: T) : CustomResult<T>
    data class Error(val exception: Throwable? = null) : CustomResult<Nothing>
    data object Loading : CustomResult<Nothing>
}

fun <T> Flow<T>?.asCustomResult(): Flow<CustomResult<T>> {
    return this?.map<T, CustomResult<T>> {
        CustomResult.Success(it)
    }
        ?.catch {
            emit(CustomResult.Error(it))
        }
        ?.onStart { emit(CustomResult.Loading) }
        ?: flowOf(CustomResult.Error(NullPointerException("Flow<Result<T>> is null")))
}

suspend fun <T> Flow<CustomResult<T>>.subscribe(
    onLoading: () -> Unit = {},
    onSuccess: (T) -> Unit = {},
    onError: (Throwable?) -> Unit = {}
) {
    this.collect { result ->
        when (result) {
            CustomResult.Loading -> onLoading.invoke()
            is CustomResult.Error -> onError.invoke(result.exception)
            is CustomResult.Success -> onSuccess.invoke(result.data)
        }
    }
}
