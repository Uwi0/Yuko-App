package com.kakapo.preference.datasource.utils

import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.model.SessionSettingsPref

suspend fun PreferenceDatasource.saveSessionSettings(settings: SessionSettingsPref): Result<Unit> {
    return runCatching {
        saveDouble(DoubleKey.FOCUS_DURATION, settings.focusDuration).getOrThrow()
        saveDouble(DoubleKey.REST_DURATION, settings.restDuration).getOrThrow()
        saveDouble(DoubleKey.CYCLE_COUNT, settings.cycleCount).getOrThrow()
    }
}

suspend fun PreferenceDatasource.getSessionSettings(): Result<SessionSettingsPref> {
    return runCatching {
        val focusDuration = getDouble(DoubleKey.FOCUS_DURATION).getOrThrow()
        val restDuration = getDouble(DoubleKey.REST_DURATION).getOrThrow()
        val cycleCount = getDouble(DoubleKey.CYCLE_COUNT).getOrThrow()

        SessionSettingsPref(
            focusDuration = focusDuration,
            restDuration = restDuration,
            cycleCount = cycleCount
        )
    }
}