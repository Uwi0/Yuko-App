package com.kakapo.database.datasource.base

import com.kakapo.database.model.TodosEntity

interface TodosLocalDatasource {
    suspend fun insertTodo(entity: TodosEntity): Result<Unit>
}