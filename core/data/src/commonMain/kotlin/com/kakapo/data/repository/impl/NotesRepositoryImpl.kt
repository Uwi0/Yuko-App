package com.kakapo.data.repository.impl

import com.kakapo.data.model.NotesParam
import com.kakapo.data.repository.base.NotesRepository
import com.kakapo.database.datasource.base.NotesLocalDatasource

class NotesRepositoryImpl(
    private val notesLocalDatasource: NotesLocalDatasource
) : NotesRepository {

    override suspend fun saveNote(param: NotesParam): Result<Unit> {
        return notesLocalDatasource.insertNote(param.toNotesEntity())
    }
}