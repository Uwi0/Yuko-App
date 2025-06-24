package org.kakapo.project.presentation.todoMenu.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("TodoViewModelKt")
class TodoViewModel: ViewModel() {

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<TodoEffect>()

    fun handleEvent(event: TodoEvent) {
        when(event) {
            TodoEvent.NavigateBack -> emit(TodoEffect.NavigateBack)
        }
    }

    private fun emit(effect: TodoEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}