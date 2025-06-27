package org.kakapo.project.presentation.todoMenu.addTodo

import com.kakapo.data.model.TodosParam
import com.kakapo.model.TodoModel

data class AddTodoState(
    val title: String = "",
    val description: String = ""
) {

    fun copy(todo: TodoModel) = copy(
        title = todo.title,
        description = todo.description
    )

    fun asTodosParam(id: Long) = TodosParam(
        id = id,
        title = title,
        description = description
    )

    companion object {
        fun default() = AddTodoState()
    }
}

sealed class AddTodoEffect {
    data object NavigateBack: AddTodoEffect()
    data class ShowError(val message: String): AddTodoEffect()
}

sealed class AddTodoEvent {
    data object NavigateBack: AddTodoEvent()
    data class ChangedTitle(val title: String): AddTodoEvent()
    data class ChangedDescription(val description: String): AddTodoEvent()
    data object SaveTodo: AddTodoEvent()
}