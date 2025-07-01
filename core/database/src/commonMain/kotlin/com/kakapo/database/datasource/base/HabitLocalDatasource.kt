package com.kakapo.database.datasource.base

import com.kakapo.database.model.HabitEntity
import kotlinx.coroutines.flow.Flow

interface HabitLocalDatasource {
    suspend fun insertHabit(entity: HabitEntity): Result<Unit>
    fun getHabits(today: Long): Flow<List<HabitEntity>>
}