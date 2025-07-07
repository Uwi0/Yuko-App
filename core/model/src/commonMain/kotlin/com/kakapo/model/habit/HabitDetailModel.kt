package com.kakapo.model.habit

data class HabitDetailModel(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val startDate: Long = 0,
)
