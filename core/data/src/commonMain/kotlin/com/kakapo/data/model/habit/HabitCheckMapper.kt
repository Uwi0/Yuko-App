package com.kakapo.data.model.habit

import com.kakapo.database.model.habit.HabitCheckEntity
import com.kakapo.model.habit.HabitCheckModel

fun HabitCheckEntity.toHabitCheckModel(): HabitCheckModel {
    return HabitCheckModel(
        id = id,
        date = date,
        isCompleted = isCompleted
    )
}