package org.kakapo.project.presentation.todoMenu.addTodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.TodosRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("AddTodoViewModelKt")
class AddTodoViewModel(
    private val todosRepository: TodosRepository
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(AddTodoState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<AddTodoEffect>()

    fun handleEvent(event: AddTodoEvent) {
        when(event) {
            is AddTodoEvent.ChangedDescription -> _uiState.update { it.copy(description = event.description) }
            is AddTodoEvent.ChangedTitle -> _uiState.update { it.copy(title = event.title) }
            AddTodoEvent.NavigateBack -> emit(AddTodoEffect.NavigateBack)
            AddTodoEvent.SaveTodo -> saveTodo()
        }
    }

    private fun saveTodo() = viewModelScope.launch {
        val todoParam = uiState.value.asTodosParam()
        val onSuccess: (Unit) -> Unit = {
            emit(AddTodoEffect.NavigateBack)
        }

        todosRepository.saveTodos(todoParam).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        emit(AddTodoEffect.ShowError(message))
    }

    private fun emit(effect: AddTodoEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}