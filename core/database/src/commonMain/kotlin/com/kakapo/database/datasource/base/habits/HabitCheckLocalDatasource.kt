package com.kakapo.database.datasource.base.habits

import com.kakapo.database.model.habit.HabitCheckEntity
import kotlinx.coroutines.flow.Flow

interface HabitCheckLocalDatasource {
    suspend fun insertTodayCheck(habitId: Long, date: Long): Result<Unit>
    suspend fun deleteTodayCheck(habitId: Long, date: Long): Result<Unit>
    fun getHabitCheckBy(habitId: Long): Flow<List<HabitCheckEntity>>
}