package org.kakapo.project.presentation.todoMenu.todos

import androidx.lifecycle.viewModelScope
import com.kakapo.common.asResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.TodosRepository
import com.kakapo.model.TodoModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.todoMenu.todos.TodosEffect.NavigateBack
import org.kakapo.project.presentation.todoMenu.todos.TodosEffect.NavigateToTodo
import org.kakapo.project.presentation.todoMenu.todos.TodosEffect.ShowError
import org.kakapo.project.presentation.todoMenu.todos.TodosEffect.TapToAddTodo
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("TodosViewModelKt")
class TodosViewModel(
    private val todosRepository: TodosRepository
): BaseViewModel<TodosState, TodosEffect, TodosEvent>(TodosState()) {

    fun initData() {
        loadTodos()
    }

    override fun handleEvent(event: TodosEvent) {
        when(event) {
            is TodosEvent.NavigateToTodo -> emit(NavigateToTodo(event.id))
            is TodosEvent.ToggleTodoIsDone -> toggleTodoIsDone(event.id, event.isDone)
            TodosEvent.NavigateBack -> emit(NavigateBack)
            TodosEvent.TapToAddTodo -> emit(TapToAddTodo)
        }
    }

    private fun loadTodos() = viewModelScope.launch {
        val onSuccess: (List<TodoModel>) -> Unit = { todos ->
            _uiState.update { it.copy(todos = todos) }
        }
        todosRepository.loadTodos().asResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun toggleTodoIsDone(id: Long, isDone: Boolean) = viewModelScope.launch {
        todosRepository.toggleTodoIsDoneById(id, isDone).fold(
            onSuccess = { loadTodos() },
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?){
        val message = throwable?.message ?: "An unknown error occurred"
        emit(ShowError(message))
    }
}