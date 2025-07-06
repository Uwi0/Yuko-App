package com.kakapo.database.datasource.implementation.habit

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.database.datasource.base.habits.HabitCheckLocalDatasource

class HabitCheckLocalDatasourceImpl(sqlDriver: SqlDriver): HabitCheckLocalDatasource {

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
}