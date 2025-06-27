package com.kakapo.data.repository.impl

import com.kakapo.data.model.HabitParam
import com.kakapo.data.repository.base.HabitRepository
import com.kakapo.database.datasource.base.HabitLocalDatasource

class HabitRepositoryImpl(
    private val habitLocalDatasource: HabitLocalDatasource
) : HabitRepository {

    override suspend fun save(habit: HabitParam): Result<Unit> {
        val entity = habit.toEntity()
        return habitLocalDatasource.insertHabit(entity)
    }
}