package com.kakapo.database.datasource.base.habits

import com.kakapo.database.model.habit.HabitEntity
import kotlinx.coroutines.flow.Flow

interface HabitLocalDatasource {
    suspend fun insertHabit(entity: HabitEntity): Result<Unit>
    suspend fun deleteHabitBy(id: Long): Result<Unit>
    fun getHabits(today: Long): Flow<List<HabitEntity>>
}