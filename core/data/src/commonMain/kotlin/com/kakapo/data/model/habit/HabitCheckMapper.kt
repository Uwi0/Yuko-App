package com.kakapo.data.model.habit

import com.kakapo.common.util.currentDay
import com.kakapo.common.util.currentTime
import com.kakapo.database.model.habit.HabitCheckEntity
import com.kakapo.model.habit.HabitCheckModel

data class HabitCheckParam(
    val habitId: Long,
    val date: Long = currentDay,
    val isCompleted: Boolean = false,
    val timeStamp: Long = currentTime
) {
    fun toHabitCheckEntity(): HabitCheckEntity {
        return HabitCheckEntity(
            habitId = habitId,
            date = date,
            timeStamp = timeStamp,
            isCompleted = isCompleted
        )
    }
}
fun HabitCheckEntity.toHabitCheckModel(): HabitCheckModel {
    return HabitCheckModel(
        id = id,
        date = date,
        isCompleted = isCompleted
    )
}