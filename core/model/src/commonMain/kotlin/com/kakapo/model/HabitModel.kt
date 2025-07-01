package com.kakapo.model

import kotlin.native.ObjCName

data class HabitModel(
    @ObjCName("habitId")
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val isGoodHabit: Boolean = true,
    val isCompleteToday: Boolean = false,
)

val dummyHabit = HabitModel(
    id = 1,
    name = "Dummy Habit",
    description = "This is a dummy habit for testing purposes.",
    isGoodHabit = true,
    isCompleteToday = false,
)