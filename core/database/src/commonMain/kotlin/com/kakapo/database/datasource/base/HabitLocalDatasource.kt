package com.kakapo.database.datasource.base

import com.kakapo.database.model.HabitEntity

interface HabitLocalDatasource {
    suspend fun insertHabit(entity: HabitEntity): Result<Unit>
}