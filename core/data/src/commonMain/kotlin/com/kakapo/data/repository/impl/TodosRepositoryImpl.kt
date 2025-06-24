package com.kakapo.data.repository.impl

import com.kakapo.data.model.TodosParam
import com.kakapo.data.model.toTodoModel
import com.kakapo.data.repository.base.TodosRepository
import com.kakapo.database.datasource.base.TodosLocalDatasource
import com.kakapo.database.model.TodosEntity
import com.kakapo.model.TodoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class TodosRepositoryImpl(
    private val todoLocalDatasource: TodosLocalDatasource
) : TodosRepository {

    override suspend fun saveTodos(param: TodosParam): Result<Unit> {
        return todoLocalDatasource.insertTodo(param.toEntity())
    }

    override suspend fun loadTodoById(id: Long): Result<TodoModel> {
        return todoLocalDatasource.getTodoById(id).map(TodosEntity::toTodoModel)
    }

    override suspend fun toggleTodoIsDoneById(
        id: Long,
        isDone: Boolean
    ): Result<Unit> {
        return todoLocalDatasource.updateTodoFinishedById(
            id = id,
            isDone = isDone,
            updateAt = Clock.System.now().epochSeconds
        )
    }

    override fun loadTodos(): Flow<List<TodoModel>> {
        return todoLocalDatasource.getTodos()
            .map { todos -> todos.map(TodosEntity::toTodoModel) }
    }
}