package org.kakapo.project.presentation.todoMenu.addTodo

sealed class AddTodoEffect {
    data object NavigateBack: AddTodoEffect()
}

sealed class AddTodoEvent {
    data object NavigateBack: AddTodoEvent()
    data object SaveTodo: AddTodoEvent()
}