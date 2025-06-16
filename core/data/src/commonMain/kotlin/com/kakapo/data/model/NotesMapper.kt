package com.kakapo.data.model

import com.kakapo.database.model.NotesEntity
import kotlinx.datetime.Clock

data class NotesParam(
    val title: String = "",
    val note: String = "",
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds(),
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
) {
    fun toNotesEntity(): NotesEntity {
        return NotesEntity(
            title = title,
            note = note,
            createdAt = createdAt,
            updatedAt = updatedAt,
            isPinned = isPinned,
            isArchived = isArchived
        )
    }
}