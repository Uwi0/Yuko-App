package com.kakapo.database.datasource.implementation.habit

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.common.asLong
import com.kakapo.database.datasource.base.habits.HabitLocalDatasource
import com.kakapo.database.model.habit.HabitEntity
import com.kakapo.database.model.habit.toHabitEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitLocalDatasourceImpl(
    sqlDriver: SqlDriver,
    private val dispatcher: CoroutineDispatcher
) : HabitLocalDatasource {

    private val habitQuery = Database.Companion(sqlDriver).habitTableQueries

    override suspend fun insertHabit(entity: HabitEntity): Result<Unit> = runCatching {
        habitQuery.insertHabit(
            name = entity.name,
            description = entity.description,
            frequency = entity.frequency,
            startDate = entity.startDate,
            isArchived = entity.isArchived.asLong(),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            habitType = entity.habitType,
            completionType = entity.completionType
        )
    }

    override suspend fun deleteHabitBy(id: Long): Result<Unit> = runCatching {
        habitQuery.deleteHabitById(id)
    }

    override fun getHabits(today: Long): Flow<List<HabitEntity>> {
        return habitQuery
            .getHabitsWithTodayCheck(today)
            .asFlow()
            .mapToList(dispatcher)
            .map { habits ->
                habits.map { it.toHabitEntity() }
            }
    }

    override suspend fun getHabitBy(id: Long): Result<HabitEntity> = runCatching {
        habitQuery.getHabitById(id).executeAsOne().toHabitEntity()
    }
}