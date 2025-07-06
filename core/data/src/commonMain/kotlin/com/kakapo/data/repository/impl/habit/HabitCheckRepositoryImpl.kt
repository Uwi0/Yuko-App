package com.kakapo.data.repository.impl.habit

import com.kakapo.data.repository.base.habit.HabitCheckRepository
import com.kakapo.database.datasource.base.habits.HabitCheckLocalDatasource

class HabitCheckRepositoryImpl(
    private val habitCheckLocalDatasource: HabitCheckLocalDatasource
) : HabitCheckRepository {

    override suspend fun saveTodayCheckBy(
        habitId: Long,
        date: Long,
    ): Result<Unit> {
        return habitCheckLocalDatasource.insertTodayCheck(habitId, date)
    }

    override suspend fun deleteTodayCheckBy(
        habitId: Long,
        date: Long,
    ): Result<Unit> {
        return habitCheckLocalDatasource.deleteTodayCheck(habitId, date)
    }
}