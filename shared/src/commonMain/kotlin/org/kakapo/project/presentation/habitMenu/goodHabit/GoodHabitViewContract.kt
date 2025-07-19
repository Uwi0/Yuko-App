package org.kakapo.project.presentation.habitMenu.goodHabit

import com.kakapo.model.habit.GoodHabitModel
import org.kakapo.project.presentation.habitMenu.model.CompletionViewMode
import org.kakapo.project.presentation.habitMenu.model.nextMode

data class GoodHabitState(
    val loading: Boolean = false,
    val completionViewMode: CompletionViewMode = CompletionViewMode.WEEKLY,
    val goodHabit: GoodHabitModel = GoodHabitModel()
) {

    fun updateNext(mode: CompletionViewMode) = copy(completionViewMode = mode.nextMode())

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
    data class ChangeCompletionMode(val mode: CompletionViewMode) : GoodHabitEvent()
}