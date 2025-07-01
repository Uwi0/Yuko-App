package org.kakapo.project.presentation.habitMenu.habits

import com.kakapo.model.HabitModel

data class HabitsState(
    val loading: Boolean = false,
    val habits: List<HabitModel> = emptyList(),
) {
    companion object {
        fun default() = HabitsState()
    }
}

sealed class HabitsEffect {
    data object NavigateBack: HabitsEffect()
    data object TapToAddHabit: HabitsEffect()
    data class ShowError(val message: String): HabitsEffect()
}

sealed class HabitsEvent {
    data object NavigateBack: HabitsEvent()
    data object TapToAddHabit: HabitsEvent()
}