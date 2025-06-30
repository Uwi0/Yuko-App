package com.kakapo.database.datasource.implementation

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.common.asLong
import com.kakapo.database.datasource.base.HabitLocalDatasource
import com.kakapo.database.model.HabitEntity

class HabitLocalDatasourceImpl(
    sqlDriver: SqlDriver
) : HabitLocalDatasource {

    private val habitQuery = Database(sqlDriver).habitTableQueries

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
            dayFrequency = entity.dayFrequency
        )
    }
}