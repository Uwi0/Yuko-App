package com.kakapo.data.model

import com.kakapo.database.model.HabitEntity

data class HabitParam(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val frequency: Long = 0,
    val startDate: Long = 0,
    val isArchived: Boolean = false,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
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
            updatedAt = updatedAt
        )
    }
}