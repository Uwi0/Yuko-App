package org.kakapo.project.presentation.todoMenu.todos

import com.kakapo.model.TodoModel

data class TodosState(
    val todos: List<TodoModel> = emptyList()
){
    companion object {
        fun default() = TodosState()
    }
}

sealed class TodosEffect {
    data object NavigateBack : TodosEffect()
    data object TapToAddTodo : TodosEffect()
    data class ShowError(val message: String) : TodosEffect()
    data class NavigateToTodo(val id: Long) : TodosEffect()
}

sealed class TodosEvent {
    data object NavigateBack : TodosEvent()
    data object TapToAddTodo : TodosEvent()
    data class NavigateToTodo(val id: Long) : TodosEvent()
    data class ToggleTodoIsDone(val id: Long, val isDone: Boolean) : TodosEvent()
}