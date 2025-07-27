package com.kakapo.database.model.habit

import com.kakapo.GetHabitsWithTodayCheck
import com.kakapo.HabitTable

data class HabitEntity(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val frequency: Long = 0,
    val completionType: String = "",
    val habitType: String = "",
    val startDate: Long = 0,
    val isArchived: Boolean = false,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val isCompletedToday: Boolean = false,
    val lastSlipDate: Long? = null
)

fun HabitTable.toHabitEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        description = description,
        frequency = frequency,
        completionType = completionType,
        habitType = habitType,
        startDate = startDate,
        isArchived = isArchived == 1L,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun GetHabitsWithTodayCheck.toHabitEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        description = description,
        frequency = frequency,
        completionType = completionType,
        habitType = habitType,
        startDate = startDate,
        isArchived = isArchived == 1L,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isCompletedToday = isCompletedToday == 1L,
        lastSlipDate = lastSlipDate
    )
}
