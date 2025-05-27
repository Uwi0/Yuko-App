package com.kakapo.data.repository.base

import com.kakapo.data.model.PomodoroSessionParam
import com.kakapo.model.SessionSettingsModel

interface PomodoroSessionRepository {
    suspend fun saveSessionSettings(sessionSettings: SessionSettingsModel): Result<Unit>
    suspend fun loadSessionSettings(): Result<SessionSettingsModel>
    suspend fun saveSessionProgress(session: PomodoroSessionParam): Result<Unit>
}