package com.kakapo.model

import kotlin.native.ObjCName

data class TodoModel(
    @ObjCName("todoId")
    val id: Long  = 0L,
    val title: String = "",
    val description: String = "",
    val isDone: Boolean = false,
) {
    companion object {
        fun default() = TodoModel(title = "Hello World", description = "This is a default todo item")
    }
}

val dummyTodosModel = listOf(
    TodoModel(id = 1L, title = "Todo 1", description = "This is the first todo item"),
    TodoModel(id = 2L, title = "Todo 2", description = "This is the second todo item"),
    TodoModel(id = 3L, title = "Todo 3", description = "This is the third todo item"),
    TodoModel(id = 4L, title = "Todo 4", description = "This is the fourth todo item"),
    TodoModel(id = 5L, title = "Todo 5", description = "This is the fifth todo item"),
    TodoModel(id = 6L, title = "Todo 6", description = "This is the sixth todo item"),
    TodoModel(id = 7L, title = "Todo 7", description = "This is the seventh todo item"),
)