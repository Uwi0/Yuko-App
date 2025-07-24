package org.kakapo.project.presentation.habitMenu.goodHabit

import com.kakapo.common.util.currentLocalDate
import com.kakapo.model.date.WeekModel
import com.kakapo.model.habit.GoodHabitModel
import kotlinx.datetime.LocalDate
import org.kakapo.project.presentation.habitMenu.model.CompletionViewMode
import org.kakapo.project.presentation.habitMenu.model.nextMode
import kotlin.time.Clock

data class GoodHabitState(
    val loading: Boolean = false,
    val completionViewMode: CompletionViewMode = CompletionViewMode.WEEKLY,
    val goodHabit: GoodHabitModel = GoodHabitModel(),
    val currentDate: LocalDate = currentLocalDate,
    val allWeeks: List<WeekModel> = emptyList()
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
    data class UpdateWeek(val index: Int): GoodHabitEvent()
}