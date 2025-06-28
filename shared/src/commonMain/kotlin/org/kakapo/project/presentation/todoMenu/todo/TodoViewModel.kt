package org.kakapo.project.presentation.todoMenu.todo

import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.TodosRepository
import com.kakapo.model.TodoModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("TodoViewModelKt")
class TodoViewModel(
    private val todosRepository: TodosRepository
): BaseViewModel<TodoState, TodoEffect, TodoEvent>(TodoState()) {

    private var todoId: Long = 0

    fun initData(id: Long) {
        todoId = id
        loadTodo(id)
    }

    override fun handleEvent(event: TodoEvent) {
        when(event) {
            TodoEvent.NavigateBack -> emit(TodoEffect.NavigateBack)
            TodoEvent.TapToEditTodo -> emit(TodoEffect.TapToEditTodo(todoId))
        }
    }

    private fun loadTodo(id: Long) = viewModelScope.launch {
        val onSuccess: (TodoModel) -> Unit = { todo ->
            _uiState.update { it.copy(todo = todo) }
        }
        todosRepository.loadTodoBy(id).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        emit(TodoEffect.ShowError(message))
    }
}