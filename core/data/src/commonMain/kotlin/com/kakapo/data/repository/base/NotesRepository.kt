package com.kakapo.data.repository.base

import com.kakapo.data.model.NotesParam
import com.kakapo.model.NotesModel
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun saveNote(param: NotesParam): Result<Unit>
    fun loadNotes(): Flow<List<NotesModel>>
}