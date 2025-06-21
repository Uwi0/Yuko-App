package com.kakapo.data.repository.impl

import co.touchlab.kermit.Logger
import com.kakapo.data.model.TodosParam
import com.kakapo.data.repository.base.TodosRepository
import com.kakapo.database.datasource.base.TodosLocalDatasource

class TodosRepositoryImpl(
    private val localDatasource: TodosLocalDatasource
) : TodosRepository {

    override suspend fun saveTodos(param: TodosParam): Result<Unit> {
        Logger.d { "saveTodos ${param.toEntity()}" }
        return localDatasource.insertTodo(param.toEntity())
    }
}