package com.kakapo.data.model

import com.kakapo.database.model.PomodoroSessionEntity

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