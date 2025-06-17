package com.kakapo.database.datasource.base

import com.kakapo.database.model.NotesEntity
import kotlinx.coroutines.flow.Flow

interface NotesLocalDatasource {
    suspend fun insertNote(entity: NotesEntity): Result<Unit>
    fun getNotes(): Flow<List<NotesEntity>>
    suspend fun getNoteById(id: Long): Result<NotesEntity>
    suspend fun deleteNoteById(id: Long): Result<Unit>
}