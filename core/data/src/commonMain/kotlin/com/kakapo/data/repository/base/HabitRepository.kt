package com.kakapo.data.repository.base

import com.kakapo.data.model.HabitParam

interface HabitRepository {
    suspend fun save(habit: HabitParam): Result<Unit>
}