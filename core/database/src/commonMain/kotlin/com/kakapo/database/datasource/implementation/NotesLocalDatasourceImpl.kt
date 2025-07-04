package com.kakapo.database.datasource.implementation

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.NotesTable
import com.kakapo.common.asLong
import com.kakapo.database.datasource.base.NotesLocalDatasource
import com.kakapo.database.model.NotesEntity
import com.kakapo.database.model.toNotesEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesLocalDatasourceImpl(
    sqlDriver: SqlDriver,
    private val dispatcher: CoroutineDispatcher
) : NotesLocalDatasource {

    private val notesQuery = Database(sqlDriver).notesTableQueries

    override suspend fun insertNote(entity: NotesEntity): Result<Unit> = runCatching {
        notesQuery.insertNote(
            title = entity.title,
            content = entity.note,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            isArchived = entity.isArchived.asLong(),
            isPinned = entity.isPinned.asLong()
        )
    }

    override suspend fun updateNote(entity: NotesEntity): Result<Unit> = runCatching {
        notesQuery.updateNote(
            title = entity.title,
            content = entity.note,
            updatedAt = entity.updatedAt,
            isArchived = entity.isArchived.asLong(),
            isPinned = entity.isPinned.asLong(),
            id = entity.id
        )
    }

    override fun getNotes(): Flow<List<NotesEntity>> = notesQuery
        .getAllNotes()
        .asFlow()
        .mapToList(dispatcher)
        .map { notes ->
            notes.map(NotesTable::toNotesEntity)
        }

    override suspend fun getNoteById(id: Long): Result<NotesEntity> = runCatching {
        notesQuery.getNoteById(id).executeAsOne().toNotesEntity()
    }

    override suspend fun deleteNoteById(id: Long): Result<Unit> = runCatching {
        notesQuery.deleteNoteById(id)
    }

}