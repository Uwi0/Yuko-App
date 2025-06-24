package com.kakapo.database.datasource.base

import com.kakapo.database.model.TodosEntity
import kotlinx.coroutines.flow.Flow

interface TodosLocalDatasource {
    suspend fun insertTodo(entity: TodosEntity): Result<Unit>
    fun getTodos(): Flow<List<TodosEntity>>
}