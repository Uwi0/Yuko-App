package com.kakapo.data.repository.base

import com.kakapo.data.model.TodosParam
import com.kakapo.model.TodoModel
import kotlinx.coroutines.flow.Flow

interface TodosRepository {
    suspend fun saveTodos(param: TodosParam): Result<Unit>
    suspend fun loadTodoById(id: Long): Result<TodoModel>
    fun loadTodos(): Flow<List<TodoModel>>
}