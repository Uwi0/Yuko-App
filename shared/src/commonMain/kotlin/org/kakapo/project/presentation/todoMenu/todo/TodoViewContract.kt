package org.kakapo.project.presentation.todoMenu.todo

import com.kakapo.model.TodoModel

data class  TodoState(
    val title: String = "",
    val description: String = "",
) {

    fun copy(todo: TodoModel) = copy(
        title = todo.title,
        description = todo.description
    )

    companion object {
        fun default() = TodoState()
    }
}

sealed class TodoEffect {
    data object NavigateBack : TodoEffect()
    data class ShowError(val message: String) : TodoEffect()
}

sealed class TodoEvent {
    data object NavigateBack : TodoEvent()
}