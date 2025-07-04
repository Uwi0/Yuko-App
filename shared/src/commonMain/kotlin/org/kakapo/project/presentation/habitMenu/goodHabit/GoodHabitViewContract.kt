package org.kakapo.project.presentation.habitMenu.goodHabit

data class GoodHabitState(
    val loading: Boolean = false
) {
    companion object {
        fun default() = GoodHabitState()
    }
}

sealed class GoodHabitEffect {
    data class ShowError(val message: String) : GoodHabitEffect()
    data object NavigateBack : GoodHabitEffect()
}

sealed class GoodHabitEvent {
    data object DeleteHabit : GoodHabitEvent()
    data object NavigateBack : GoodHabitEvent()
}