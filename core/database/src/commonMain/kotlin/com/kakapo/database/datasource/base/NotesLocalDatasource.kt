package com.kakapo.database.datasource.base

import com.kakapo.database.model.NotesEntity

interface NotesLocalDatasource {
    suspend fun insertNote(entity: NotesEntity): Result<Unit>
}