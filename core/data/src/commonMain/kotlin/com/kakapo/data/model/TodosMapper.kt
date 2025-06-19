package com.kakapo.data.model

import com.kakapo.database.model.TodosEntity
import kotlin.time.Clock

data class TodosParam(
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val isDone: Boolean = false,
    val dueDate: Long = 0L,
    val createdAt: Long = Clock.System.now().epochSeconds,
    val updatedAt: Long = Clock.System.now().epochSeconds,
    val pinned: Boolean = false,
    val priority: Long = 0L,
) {
    fun toEntity() = TodosEntity(
        id = id,
        title = title,
        description = description,
        isDone = isDone,
        dueDate = dueDate,
        createdAt = createdAt,
        updatedAt = updatedAt,
        pinned = pinned,
        priority = priority
    )
}