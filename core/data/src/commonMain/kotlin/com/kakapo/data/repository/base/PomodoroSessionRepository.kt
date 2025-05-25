package com.kakapo.data.repository.base

import com.kakapo.data.model.PomodoroSessionParam

interface PomodoroSessionRepository {
    suspend fun saveSession(session: PomodoroSessionParam): Result<Unit>
}