package org.kakapo.project.presentation.todoMenu.addTodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("AddTodoViewModelKt")
class AddTodoViewModel: ViewModel() {

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<AddTodoEffect>()

    fun handleEvent(event: AddTodoEvent) {
        when(event) {
            AddTodoEvent.NavigateBack -> emit(AddTodoEffect.NavigateBack)
            AddTodoEvent.SaveTodo -> TODO()
        }
    }

    private fun emit(effect: AddTodoEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}