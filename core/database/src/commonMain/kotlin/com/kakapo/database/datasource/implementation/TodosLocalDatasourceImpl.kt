package com.kakapo.database.datasource.implementation

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.common.asLong
import com.kakapo.database.datasource.base.TodosLocalDatasource
import com.kakapo.database.model.TodosEntity

class TodosLocalDatasourceImpl(sqlDriver: SqlDriver): TodosLocalDatasource {

    private val todoQuery = Database(sqlDriver).todosTableQueries

    override suspend fun insertTodo(entity: TodosEntity): Result<Unit> = runCatching {
        todoQuery.insertTodo(
            title = entity.title,
            description = entity.description,
            isDone = entity.isDone.asLong(),
            dueDate = entity.dueDate,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            pinned = entity.pinned.asLong(),
            priority = entity.priority
        )
    }
}