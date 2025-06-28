package org.kakapo.project.presentation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E, V>(
    initialState: S
): ViewModel() {

    @NativeCoroutinesState
    val uiState: StateFlow<S> get() = _uiState.asStateFlow()
    open val _uiState = MutableStateFlow(initialState)

    @NativeCoroutines
    val uiEffect: SharedFlow<E> get() = _uiEffect.asSharedFlow()
    open val _uiEffect = MutableSharedFlow<E>()

    abstract fun handleEvent(event: V)

    fun emit(effect: E) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}