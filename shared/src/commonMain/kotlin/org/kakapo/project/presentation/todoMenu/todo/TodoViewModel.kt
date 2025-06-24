package org.kakapo.project.presentation.todoMenu.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.TodosRepository
import com.kakapo.model.TodoModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("TodoViewModelKt")
class TodoViewModel(
    private val todosRepository: TodosRepository
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(TodoState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<TodoEffect>()

    private var todoId: Long = 0

    fun initData(id: Long) {
        todoId = id
        loadTodo(id)
    }

    fun handleEvent(event: TodoEvent) {
        when(event) {
            TodoEvent.NavigateBack -> emit(TodoEffect.NavigateBack)
        }
    }

    private fun loadTodo(id: Long) = viewModelScope.launch {
        val onSuccess: (TodoModel) -> Unit = { todo ->
            _uiState.update { it.copy(todo = todo) }
        }
        todosRepository.loadTodoById(id).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        emit(TodoEffect.ShowError(message))
    }

    private fun emit(effect: TodoEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}