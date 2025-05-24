package com.kakapo.database.datasource.implementation

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.database.datasource.base.PomodoroSessionLocalDatasource
import com.kakapo.database.model.PomodoroSessionEntity

class PomodoroSessionLocalDatasourceImpl(sqlDriver: SqlDriver): PomodoroSessionLocalDatasource {

    private val pomodoroSessionQuery = Database(sqlDriver)

    override suspend fun insertSession(pomodoro: PomodoroSessionEntity): Result<Unit> {
        TODO("Not yet implemented")
    }
}