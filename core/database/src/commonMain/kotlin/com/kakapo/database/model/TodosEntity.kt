package com.kakapo.database.model

import com.kakapo.TodosTable

data class TodosEntity(
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val isDone: Boolean = false,
    val dueDate: Long = 0L,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val pinned: Boolean = false,
    val priority: Long = 0L,
)

fun TodosTable.toTodosEntity(): TodosEntity {
    return TodosEntity(
        id = id,
        title = title,
        description = description,
        isDone = isDone != 0L,
        dueDate = dueDate,
        createdAt = createdAt,
        updatedAt = updatedAt,
        pinned = pinned != 0L,
        priority = priority
    )
}
