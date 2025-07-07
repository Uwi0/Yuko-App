package com.kakapo.data.repository.base.habit

import com.kakapo.data.model.habit.HabitParam
import com.kakapo.model.habit.HabitModel
import com.kakapo.model.habit.HabitItemModel
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun save(habit: HabitParam): Result<Unit>
    suspend fun deleteHabitBy(id: Long): Result<Unit>
    suspend fun loadHabitDetailBy(id: Long): Result<HabitModel>
    fun loadHabits(today: Long): Flow<List<HabitItemModel>>
}