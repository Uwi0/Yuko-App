package org.kakapo.project.presentation.habitMenu.habits

data class HabitsState(
    val loading: Boolean = false
) {
    companion object {
        fun default() = HabitsState()
    }
}

sealed class HabitsEffect {
    data object NavigateBack: HabitsEffect()
    data object TapToAddHabit: HabitsEffect()
}

sealed class HabitsEvent {
    data object NavigateBack: HabitsEvent()
    data object TapToAddHabit: HabitsEvent()
}