package com.kakapo.model.habit

data class HabitModel(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val startDate: Long = 0,
)
