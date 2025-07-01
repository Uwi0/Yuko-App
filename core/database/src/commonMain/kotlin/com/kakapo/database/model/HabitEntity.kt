package com.kakapo.database.model

import com.kakapo.GetHabitsWithTodayCheck

data class HabitEntity(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val frequency: Long = 0,
    val dayFrequency: String = "",
    val habitType: Long = 1,
    val startDate: Long = 0,
    val isArchived: Boolean = false,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val isCompletedToday: Boolean = false
)

fun GetHabitsWithTodayCheck.toHabitEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        description = description,
        frequency = frequency,
        dayFrequency = dayFrequency,
        habitType = habitType,
        startDate = startDate,
        isArchived = isArchived == 1L,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isCompletedToday = isCompletedToday == 1L
    )
}
