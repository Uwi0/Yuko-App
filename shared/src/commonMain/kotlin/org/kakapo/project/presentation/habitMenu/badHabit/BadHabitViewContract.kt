package org.kakapo.project.presentation.habitMenu.badHabit

data class BadHabitState(
    val loading: Boolean = false
) {
    companion object {
        fun default() = BadHabitState()
    }
}

sealed class BadHabitEffect {
    data class ShowError(val message: String) : BadHabitEffect()
    data object NavigateBack : BadHabitEffect()
}

sealed class BadHabitEvent {
    data object DeleteHabit : BadHabitEvent()
    data object NavigateBack : BadHabitEvent()
}