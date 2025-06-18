package com.kakapo.data.repository.impl

import co.touchlab.kermit.Logger
import com.kakapo.common.mapEach
import com.kakapo.data.model.NotesParam
import com.kakapo.data.model.toNotesModel
import com.kakapo.data.repository.base.NotesRepository
import com.kakapo.database.datasource.base.NotesLocalDatasource
import com.kakapo.database.model.NotesEntity
import com.kakapo.model.NotesModel
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val notesLocalDatasource: NotesLocalDatasource
) : NotesRepository {

    override suspend fun saveNote(param: NotesParam): Result<Unit> {
        Logger.d { "param: $param" }
        return if (param.id == 0L) notesLocalDatasource.insertNote(param.toNotesEntity())
        else notesLocalDatasource.updateNote(param.toNotesEntity())
    }

    override fun loadNotes(): Flow<List<NotesModel>> {
        return notesLocalDatasource.getNotes().mapEach(NotesEntity::toNotesModel)
    }

    override suspend fun loadNoteById(id: Long): Result<NotesModel> {
        return notesLocalDatasource.getNoteById(id).mapCatching(NotesEntity::toNotesModel)
    }

    override suspend fun deleteNoteById(id: Long): Result<Unit> {
        return notesLocalDatasource.deleteNoteById(id)
    }
}