package com.kakapo.database.model.habit

import co.touchlab.kermit.Logger
import com.kakapo.GetHabitCheckByHabitId
import com.kakapo.HabitCheckTable

data class HabitCheckEntity(
    val id: Long = 0,
    val habitId: Long = 0,
    val date: Long = 0,
    val timeStamp: Long = 0,
    val completionCount: Long = 1,
    val isCompleted: Boolean = false
)

fun GetHabitCheckByHabitId.toHabitCheckEntity(): HabitCheckEntity {
    return HabitCheckEntity(
        habitId = habitId,
        date = date,
        timeStamp = lastEntry ?: 0,
        completionCount = totalCompletionCount ?: 0,
        isCompleted = isCompleted == 1L
    )
}