package com.kakapo.database.datasource.implementation.habit

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.database.datasource.base.habits.HabitCheckLocalDatasource
import com.kakapo.database.model.habit.HabitCheckEntity
import com.kakapo.database.model.habit.toHabitCheckEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitCheckLocalDatasourceImpl(
    sqlDriver: SqlDriver,
    private val dispatcher: CoroutineDispatcher
): HabitCheckLocalDatasource {

    private val habitCheckQuery = Database.Companion(sqlDriver).habitCheckTableQueries

    override suspend fun insertTodayCheck(
        habitId: Long,
        date: Long
    ): Result<Unit> = runCatching {
        habitCheckQuery.insertTodayCheck(habitId, date)
    }

    override suspend fun deleteTodayCheck(
        habitId: Long,
        date: Long
    ): Result<Unit> = runCatching {
        habitCheckQuery.deleteTodayCheck(habitId, date)
    }

    override fun getHabitCheckBy(habitId: Long): Flow<List<HabitCheckEntity>> {
        return habitCheckQuery
            .getHabitCheckByHabitId(habitId)
            .asFlow()
            .mapToList(dispatcher)
            .map { habitChecks -> habitChecks.map { it.toHabitCheckEntity() } }
    }
}