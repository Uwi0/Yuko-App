package org.kakapo.project.presentation.habitMenu.habits

import com.kakapo.model.habit.HabitItemModel
import com.kakapo.model.habit.HabitType

data class HabitsState(
    val loading: Boolean = false,
    val habits: List<HabitItemModel> = emptyList(),
) {
    companion object {
        fun default() = HabitsState()
    }
}

sealed class HabitsEffect {
    data object NavigateBack: HabitsEffect()
    data object TapToAddHabit: HabitsEffect()
    data class ShowError(val message: String): HabitsEffect()
    data class NavigateToGoodHabit(val id: Long): HabitsEffect()
    data class NavigateToBadHabit(val id: Long): HabitsEffect()
}

sealed class HabitsEvent {
    data object NavigateBack: HabitsEvent()
    data object TapToAddHabit: HabitsEvent()
    data class TappedHabit(val id: Long, val type: HabitType): HabitsEvent()
    data class TrackCompletion(val model: HabitItemModel): HabitsEvent()
    data class CheckedGoodHabit(val id: Long, val isChecked: Boolean): HabitsEvent()
}