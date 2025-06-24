package com.kakapo.data.repository.impl

import com.kakapo.data.model.TodosParam
import com.kakapo.data.model.toTodoModel
import com.kakapo.data.repository.base.TodosRepository
import com.kakapo.database.datasource.base.TodosLocalDatasource
import com.kakapo.database.model.TodosEntity
import com.kakapo.model.TodoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodosRepositoryImpl(
    private val todoLocalDatasource: TodosLocalDatasource
) : TodosRepository {

    override suspend fun saveTodos(param: TodosParam): Result<Unit> {
        return todoLocalDatasource.insertTodo(param.toEntity())
    }

    override fun loadTodos(): Flow<List<TodoModel>> {
        return todoLocalDatasource.getTodos()
            .map { todos -> todos.map (TodosEntity::toTodoModel) }
    }
}