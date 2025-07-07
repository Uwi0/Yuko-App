package com.kakapo.data.model.habit

import com.kakapo.common.util.asDayClean
import com.kakapo.database.model.habit.HabitEntity
import com.kakapo.model.habit.HabitModel
import com.kakapo.model.habit.HabitItemModel
import com.kakapo.model.habit.toHabitType

data class HabitParam(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val frequency: Long = 0,
    val startDate: Long = 0,
    val isArchived: Boolean = false,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val habitType: Long = 1,
) {
    fun toEntity(): HabitEntity {
        return HabitEntity(
            id = id,
            name = name,
            description = description,
            frequency = frequency,
            startDate = startDate,
            isArchived = isArchived,
            createdAt = createdAt,
            updatedAt = updatedAt,
            habitType = habitType
        )
    }
}

fun HabitEntity.toHabitModel(): HabitItemModel {
    return HabitItemModel(
        id = id,
        name = name,
        description = description,
        habitType = habitType.toHabitType(),
        isCompleteToday = isCompletedToday,
        lastSlipDate = lastSlipDate.asDayClean()
    )
}

fun HabitEntity.toHabitDetailModel(): HabitModel {
    return HabitModel(
        id = id,
        name = name,
        description = description,
        startDate = startDate
    )
}