package org.kakapo.project.presentation.habitMenu.addHabit

data class AddHabitState(
    val name: String = "",
    val description: String = "",
) {
    companion object {
        fun default() = AddHabitState()
    }
}

sealed class AddHabitEffect {
    data object NavigateBack: AddHabitEffect()
}

sealed class AddHabitEvent {
    data object NavigateBack: AddHabitEvent()
}