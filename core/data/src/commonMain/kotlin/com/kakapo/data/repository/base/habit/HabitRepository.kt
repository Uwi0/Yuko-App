package com.kakapo.data.repository.base.habit

import com.kakapo.data.model.habit.HabitParam
import com.kakapo.model.habit.HabitDetailModel
import com.kakapo.model.habit.HabitModel
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun save(habit: HabitParam): Result<Unit>
    suspend fun deleteHabitBy(id: Long): Result<Unit>
    suspend fun loadHabitDetailBy(id: Long): Result<HabitDetailModel>
    fun loadHabitsToday(): Flow<List<HabitModel>>
}