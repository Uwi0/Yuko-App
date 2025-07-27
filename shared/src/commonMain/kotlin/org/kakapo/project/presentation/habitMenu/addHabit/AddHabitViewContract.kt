package org.kakapo.project.presentation.habitMenu.addHabit

import com.kakapo.common.util.currentDay
import com.kakapo.data.model.habit.HabitParam
import com.kakapo.model.habit.CompletionType
import com.kakapo.model.habit.HabitType
import com.kakapo.model.habit.toLong
import com.kakapo.model.reminder.ReminderDays
import kotlin.native.ObjCName

data class AddHabitState(
    val name: String = "",
    val description: String = "",
    val type: HabitType = HabitType.GOOD,
    val setReminder: Boolean = false,
    val selectedDays: List<ReminderDays> = emptyList(),
    @ObjCName("targetFrequencyKt")
    val targetFrequency: Int = 0,
) {
    val isToggleOn: Boolean
        get() = type == HabitType.GOOD

    val completionType: CompletionType get() {
        return if (targetFrequency == 0 || targetFrequency == 1) CompletionType.Single
        else CompletionType.Frequency
    }

    fun toggleType(): AddHabitState {
        return when (type) {
            HabitType.GOOD -> copy(type = HabitType.BAD)
            HabitType.BAD -> copy(type = HabitType.GOOD)
        }
    }

    fun asHabitParam(): HabitParam{
        val frequency = if (targetFrequency == 0 || targetFrequency == 1) 1
        else targetFrequency.toLong()
        return HabitParam(
            name = name,
            description = description,
            habitType = type.name,
            startDate = currentDay,
            createdAt = currentDay,
            completionType = completionType,
            frequency = frequency
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
    data class QuantityChanged(val frequency: Int): AddHabitEvent()
}


