package com.kakapo.model.habit

import kotlin.native.ObjCName

data class HabitItemModel(
    @ObjCName("habitId")
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val habitType: HabitType = HabitType.GOOD,
    val isCompleteToday: Boolean = false,
    val completionType: CompletionType = CompletionType.Single,
    val targetFrequency: Long = 0,
    val lastSlipDate: Long = 0,
) {
    val isGoodHabit: Boolean
        get() = habitType == HabitType.GOOD
}

val dummyHabit = HabitItemModel(
    id = 1,
    name = "Dummy Habit",
    description = "This is a dummy habit for testing purposes.",
    isCompleteToday = false,
)