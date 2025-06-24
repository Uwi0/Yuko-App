package com.kakapo.database.datasource.base

import com.kakapo.database.model.TodosEntity
import kotlinx.coroutines.flow.Flow

interface TodosLocalDatasource {
    suspend fun insertTodo(entity: TodosEntity): Result<Unit>
    suspend fun getTodoById(id: Long): Result<TodosEntity>
    suspend fun updateTodoFinishedById(id: Long, isDone: Boolean, updateAt: Long): Result<Unit>
    fun getTodos(): Flow<List<TodosEntity>>
}