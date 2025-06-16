package com.kakapo.database.datasource.implementation

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.common.asLong
import com.kakapo.database.datasource.base.NotesLocalDatasource
import com.kakapo.database.model.NotesEntity

class NotesLocalDatasourceImpl(sqlDriver: SqlDriver): NotesLocalDatasource {

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
}