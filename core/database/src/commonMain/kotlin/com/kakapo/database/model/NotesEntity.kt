package com.kakapo.database.model

data class NotesEntity(
    val id: Long = 0L,
    val title: String = "",
    val note: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
)
