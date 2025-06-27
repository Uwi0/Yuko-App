package com.kakapo.data.model

import com.kakapo.database.model.NotesEntity
import com.kakapo.model.NotesModel
import kotlin.time.Clock

data class NotesParam(
    val id: Long = 0L,
    val title: String = "",
    val note: String = "",
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds(),
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
) {
    fun toNotesEntity(): NotesEntity {
        return NotesEntity(
            id = id,
            title = title,
            note = note,
            createdAt = createdAt,
            updatedAt = updatedAt,
            isPinned = isPinned,
            isArchived = isArchived
        )
    }
}

fun NotesEntity.toNotesModel() = NotesModel(
    id = id,
    title = title,
    note = note
)