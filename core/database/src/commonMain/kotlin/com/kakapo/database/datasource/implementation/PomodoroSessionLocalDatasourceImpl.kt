package com.kakapo.database.datasource.implementation

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.common.asLong
import com.kakapo.database.datasource.base.PomodoroSessionLocalDatasource
import com.kakapo.database.model.PomodoroSessionEntity

class PomodoroSessionLocalDatasourceImpl(sqlDriver: SqlDriver): PomodoroSessionLocalDatasource {

    private val pomodoroSessionQuery = Database(sqlDriver).pomodoroSessionTableQueries

    override suspend fun insertSession(pomodoro: PomodoroSessionEntity): Result<Unit> = runCatching {
        pomodoroSessionQuery.insertSession(
            startTime = pomodoro.startTime,
            endTime = pomodoro.endTime,
            duration = pomodoro.duration,
            pointEarned = pomodoro.pointEarned,
            isCompleted = pomodoro.isCompleted.asLong(),
            reasonFailed = pomodoro.reasonFailed,
            note = pomodoro.note
        )
    }

    override suspend fun getTotalPoints(): Result<Long> = runCatching {
        pomodoroSessionQuery.getTotalPoints().executeAsOne().TotalPointEarned ?: 0
    }
}