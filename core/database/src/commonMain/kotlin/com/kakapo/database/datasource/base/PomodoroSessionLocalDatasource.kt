package com.kakapo.database.datasource.base

import com.kakapo.database.model.PomodoroSessionEntity

interface PomodoroSessionLocalDatasource {
    suspend fun insertSession(pomodoro: PomodoroSessionEntity): Result<Unit>
}