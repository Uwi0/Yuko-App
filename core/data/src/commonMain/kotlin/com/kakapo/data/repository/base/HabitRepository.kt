package com.kakapo.data.repository.base

import com.kakapo.data.model.HabitParam
import com.kakapo.model.HabitModel
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun save(habit: HabitParam): Result<Unit>
    fun loadHabitsToday(): Flow<List<HabitModel>>
}