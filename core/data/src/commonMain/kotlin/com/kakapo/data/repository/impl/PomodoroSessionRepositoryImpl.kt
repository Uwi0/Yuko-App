package com.kakapo.data.repository.impl

import com.kakapo.data.model.PomodoroSessionParam
import com.kakapo.data.repository.base.PomodoroSessionRepository
import com.kakapo.database.datasource.base.PomodoroSessionLocalDatasource
import com.kakapo.preference.datasource.base.PreferenceDatasource

class PomodoroSessionRepositoryImpl (
    private val localDatasource: PomodoroSessionLocalDatasource,
    private val preference: PreferenceDatasource
) : PomodoroSessionRepository {

    override suspend fun saveSession(session: PomodoroSessionParam): Result<Unit> {
        return localDatasource.insertSession(session.toEntity())
    }

}