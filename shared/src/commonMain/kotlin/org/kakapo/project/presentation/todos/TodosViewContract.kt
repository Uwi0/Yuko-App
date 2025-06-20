package org.kakapo.project.presentation.todos

sealed class TodosEffect {
    data object NavigateBack : TodosEffect()
    data object TapToAddTodo: TodosEffect()
}

sealed class TodosEvent {
    data object NavigateBack : TodosEvent()
    data object TapToAddTodo: TodosEvent()
}