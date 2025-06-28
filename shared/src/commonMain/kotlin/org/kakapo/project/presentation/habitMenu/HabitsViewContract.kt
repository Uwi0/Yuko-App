package org.kakapo.project.presentation.habitMenu

sealed class HabitsEffect {
    data object NavigateBack: HabitsEffect()
    data object TapToAddHabit: HabitsEffect()
}

sealed class HabitsEvent {
    data object OnBackPressed: HabitsEvent()
    data object OnAddHabitClicked: HabitsEvent()
}