package com.kakapo.model.habit

import kotlin.native.ObjCName

data class HabitModel(
    @ObjCName("habitId")
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val habitType: HabitType = HabitType.GOOD,
    val isCompleteToday: Boolean = false,
    val lastSlipDate: Long = 0,
) {
    val isGoodHabit: Boolean
        get() = habitType == HabitType.GOOD
}

val dummyHabit = HabitModel(
    id = 1,
    name = "Dummy Habit",
    description = "This is a dummy habit for testing purposes.",
    isCompleteToday = false,
)