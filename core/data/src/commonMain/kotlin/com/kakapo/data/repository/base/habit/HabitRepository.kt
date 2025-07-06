package com.kakapo.data.repository.base.habit

import com.kakapo.data.model.HabitParam
import com.kakapo.model.habit.HabitModel
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun save(habit: HabitParam): Result<Unit>
    suspend fun deleteHabitBy(id: Long): Result<Unit>
    fun loadHabitsToday(): Flow<List<HabitModel>>
}