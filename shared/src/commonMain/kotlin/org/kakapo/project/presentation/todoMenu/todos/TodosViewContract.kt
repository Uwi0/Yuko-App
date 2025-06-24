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
    data object NavigateToTodo : TodosEffect()
}

sealed class TodosEvent {
    data object NavigateBack : TodosEvent()
    data object TapToAddTodo : TodosEvent()
    data object NavigateToTodo : TodosEvent()
}