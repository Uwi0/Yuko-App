package com.kakapo.model.habit

data class GoodHabitModel(
    val name: String = "",
    val description: String = "",
    val missedCount: Int = 0,
    val currentStreak: Int = 0,
    val totalComplete: Int = 0,
    val bestStreak: Int = 0,
    val completionThisMonth: Int = 0,
    val startDate: Long = 0,
    val calendarMap: Map<Long, Boolean> = emptyMap(),
)
