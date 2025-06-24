package com.kakapo.database.datasource.implementation

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database
import com.kakapo.TodosTable
import com.kakapo.common.asLong
import com.kakapo.database.datasource.base.TodosLocalDatasource
import com.kakapo.database.model.TodosEntity
import com.kakapo.database.model.toTodosEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodosLocalDatasourceImpl(
    sqlDriver: SqlDriver,
    private val dispatcher: CoroutineDispatcher
): TodosLocalDatasource {

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

    override suspend fun getTodoById(id: Long): Result<TodosEntity> = runCatching {
        todoQuery.getTodoById(id).executeAsOne().toTodosEntity()
    }

    override suspend fun updateTodoFinishedById(
        id: Long,
        isDone: Boolean,
        updateAt: Long
    ): Result<Unit> = runCatching {
        todoQuery.updateFinishedTodoById(
            id = id,
            isDone = isDone.asLong(),
            updatedAt = updateAt
        )
    }

    override fun getTodos(): Flow<List<TodosEntity>> {
        return todoQuery.getTodos()
            .asFlow()
            .mapToList(dispatcher)
            .map{ todos -> todos.map(TodosTable::toTodosEntity) }
    }
}