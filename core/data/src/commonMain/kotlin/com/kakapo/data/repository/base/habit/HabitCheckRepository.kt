package com.kakapo.data.repository.base.habit

import com.kakapo.model.habit.HabitCheckModel
import kotlinx.coroutines.flow.Flow

interface HabitCheckRepository {
    suspend fun saveTodayCheckBy(habitId: Long, date: Long): Result<Unit>
    suspend fun deleteTodayCheckBy(habitId: Long, date: Long): Result<Unit>
    fun loadTodayCheckBy(habitId: Long): Flow<List<HabitCheckModel>>
}