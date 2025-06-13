package com.kakapo.data.model

import com.kakapo.database.model.PomodoroSessionEntity
import com.kakapo.model.SessionSettingsModel
import com.kakapo.preference.model.SessionSettingsPref

data class PomodoroSessionParam(
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val isCompleted: Boolean,
    val reasonFailed: String? = null,
    val note: String? = null
) {
    val pointEarned get() = if (isCompleted) duration / 5 else -5L

    fun toEntity() = PomodoroSessionEntity(
        startTime = startTime,
        endTime = endTime,
        duration = duration,
        pointEarned = pointEarned,
        isCompleted = isCompleted,
        reasonFailed = reasonFailed,
        note = note
    )

}

fun SessionSettingsModel.toSessionSettingsPref() = SessionSettingsPref(
    focusDuration = focusDuration,
    restDuration = restDuration,
    cycleCount = cycleCount
)

fun SessionSettingsPref.toSessionSettingsModel(): SessionSettingsModel {
    val focusDuration = if (focusDuration <= 0) 25.0 else focusDuration
    return SessionSettingsModel(
        focusDuration = focusDuration,
        restDuration = restDuration,
        cycleCount = cycleCount
    )
}