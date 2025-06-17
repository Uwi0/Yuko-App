package com.kakapo.database.model

import com.kakapo.NotesTable

data class NotesEntity(
    val id: Long = 0L,
    val title: String = "",
    val note: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
)

fun NotesTable.toNotesEntity(): NotesEntity {
    return NotesEntity(
        id = id,
        title = title,
        note = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isPinned = isPinned != 0L,
        isArchived = isArchived != 0L
    )
}