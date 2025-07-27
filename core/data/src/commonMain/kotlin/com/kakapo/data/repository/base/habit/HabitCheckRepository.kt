package com.kakapo.data.repository.base.habit

import com.kakapo.data.model.habit.HabitCheckParam
import com.kakapo.model.habit.HabitCheckModel
import kotlinx.coroutines.flow.Flow

interface HabitCheckRepository {
    suspend fun saveHabitCheck(param: HabitCheckParam): Result<Unit>
    suspend fun deleteTodayCheckBy(habitId: Long, date: Long): Result<Unit>
    fun loadHabitChecksBy(habitId: Long): Flow<List<HabitCheckModel>>
}