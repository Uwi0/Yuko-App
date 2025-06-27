package com.kakapo.database.model

data class HabitEntity(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val frequency: Long = 0,
    val startDate: Long = 0,
    val isArchived: Boolean = false,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
)
