package org.kakapo.project.presentation.todoMenu.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("TodosViewModelKt")
class TodosViewModel: ViewModel() {

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private var _uiEffect = MutableSharedFlow<TodosEffect>()

    fun handleEvent(event: TodosEvent) {
        when(event) {
            TodosEvent.NavigateBack -> emit(TodosEffect.NavigateBack)
            TodosEvent.TapToAddTodo -> emit(TodosEffect.TapToAddTodo)
        }
    }

    private fun emit(effect: TodosEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}