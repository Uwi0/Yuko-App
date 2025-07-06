package com.kakapo.database.model.habit

data class HabitCheckEntity(
    val id: Long = 0,
    val habitId: Long = 0,
    val date: Long = 0,
    val isCompleted: Boolean = false
)
