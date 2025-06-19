package com.kakapo.database.model

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
