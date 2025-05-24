package com.kakapo.database.model

import com.kakapo.PomodoroSessionTable
import com.kakapo.common.asLong

data class PomodoroSessionEntity(
    val id: Long? = null,
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val pointEarned: Long,
    val isCompleted: Boolean,
    val reasonFailed: String? = null,
    val note: String? = null
) {
    fun toTable(): PomodoroSessionTable {
        return PomodoroSessionTable(
            id = id ?: 0L,
            startTime = startTime,
            endTime = endTime,
            duration = duration,
            pointEarned = pointEarned,
            isCompleted = isCompleted.asLong(),
            reasonFailed = reasonFailed,
            note = note,
        )
    }
}

