package com.kakapo.data.model

import com.kakapo.database.model.PomodoroSessionEntity
import com.kakapo.model.SessionSettingsModel
import com.kakapo.preference.model.SessionSettingsPref

data class PomodoroSessionParam(
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val pointEarned: Long,
    val isCompleted: Boolean,
    val reasonFailed: String? = null,
    val note: String? = null
) {

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

fun SessionSettingsPref.toSessionSettingsModel() = SessionSettingsModel(
    focusDuration = focusDuration,
    restDuration = restDuration,
    cycleCount = cycleCount
)