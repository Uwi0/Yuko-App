package com.kakapo.data.repository.impl

import com.kakapo.data.model.PomodoroSessionParam
import com.kakapo.data.model.toSessionSettingsModel
import com.kakapo.data.model.toSessionSettingsPref
import com.kakapo.data.repository.base.PomodoroSessionRepository
import com.kakapo.database.datasource.base.PomodoroSessionLocalDatasource
import com.kakapo.model.SessionSettingsModel
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.getSessionSettings
import com.kakapo.preference.datasource.utils.saveSessionSettings

class PomodoroSessionRepositoryImpl (
    private val localDatasource: PomodoroSessionLocalDatasource,
    private val preference: PreferenceDatasource
) : PomodoroSessionRepository {

    override suspend fun saveSessionSettings(settings: SessionSettingsModel): Result<Unit> {
        return preference.saveSessionSettings(settings.toSessionSettingsPref())
    }

    override suspend fun loadSessionSettings(): Result<SessionSettingsModel> {
        return preference.getSessionSettings().mapCatching { it.toSessionSettingsModel() }
    }

    override suspend fun saveSessionProgress(session: PomodoroSessionParam): Result<Unit> {
        return localDatasource.insertSession(session.toEntity())
    }

    override suspend fun loadTotalPoints(): Result<Long> {
        return localDatasource.getTotalPoints()
    }

}