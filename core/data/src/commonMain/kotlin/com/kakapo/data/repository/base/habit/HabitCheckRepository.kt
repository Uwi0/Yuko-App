package com.kakapo.data.repository.base.habit

interface HabitCheckRepository {
    suspend fun saveTodayCheckBy(habitId: Long, date: Long): Result<Unit>
    suspend fun deleteTodayCheckBy(habitId: Long, date: Long): Result<Unit>
}