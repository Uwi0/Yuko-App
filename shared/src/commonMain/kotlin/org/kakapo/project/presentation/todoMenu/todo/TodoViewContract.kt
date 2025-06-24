package org.kakapo.project.presentation.todoMenu.todo

sealed class TodoEffect {
    data object NavigateBack : TodoEffect()
    data class ShowError(val message: String) : TodoEffect()
}

sealed class TodoEvent {
    data object NavigateBack : TodoEvent()
}