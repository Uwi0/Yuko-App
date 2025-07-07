package com.kakapo.model.habit

data class HabitCheckModel(
    val id: Long = 0,
    val date: Long = 0,
    val isCompleted: Boolean = false
)
