package com.kakapo.data.repository.base

import com.kakapo.data.model.NotesParam

interface NotesRepository {
    suspend fun saveNote(param: NotesParam): Result<Unit>
}