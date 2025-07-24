package org.kakapo.project.presentation.habitMenu.addHabit

import com.kakapo.common.util.currentDay
import com.kakapo.common.util.currentTime
import com.kakapo.data.model.habit.HabitParam
import com.kakapo.model.habit.HabitType
import com.kakapo.model.habit.toLong
import com.kakapo.model.reminder.ReminderDays

data class AddHabitState(
    val name: String = "",
    val description: String = "",
    val type: HabitType = HabitType.GOOD,
    val setReminder: Boolean = false,
    val selectedDays: List<ReminderDays> = emptyList(),
) {
    val isToggleOn: Boolean
        get() = type == HabitType.GOOD

    fun toggleType(): AddHabitState {
        return when (type) {
            HabitType.GOOD -> copy(type = HabitType.BAD)
            HabitType.BAD -> copy(type = HabitType.GOOD)
        }
    }

    fun asHabitParam(): HabitParam{
        return HabitParam(
            name = name,
            description = description,
            habitType = type.toLong(),
            startDate = currentDay,
            createdAt = currentDay
        )
    }

    companion object {
        fun default() = AddHabitState()
    }
}

sealed class AddHabitEffect {
    data class ShowError(val message: String): AddHabitEffect()
    data object NavigateBack: AddHabitEffect()
}

sealed class AddHabitEvent {
    data object NavigateBack: AddHabitEvent()
    data class NameChanged(val name: String): AddHabitEvent()
    data class DescriptionChanged(val description: String): AddHabitEvent()
    data object ToggleType: AddHabitEvent()
    data object SaveHabit: AddHabitEvent()
}


