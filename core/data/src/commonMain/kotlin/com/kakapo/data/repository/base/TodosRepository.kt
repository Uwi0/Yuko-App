package com.kakapo.data.repository.base

import com.kakapo.data.model.TodosParam

interface TodosRepository {
    suspend fun saveTodos(param: TodosParam): Result<Unit>
}