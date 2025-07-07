package com.kakapo.database.model.habit

import com.kakapo.HabitCheckTable

data class HabitCheckEntity(
    val id: Long = 0,
    val habitId: Long = 0,
    val date: Long = 0,
    val isCompleted: Boolean = false
)

fun HabitCheckTable.toHabitCheckEntity(): HabitCheckEntity {
    return HabitCheckEntity(
        id = id,
        habitId = habitId,
        date = date,
        isCompleted = isCompleted == 1L
    )
}