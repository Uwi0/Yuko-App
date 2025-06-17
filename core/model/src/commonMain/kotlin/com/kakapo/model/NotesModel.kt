package com.kakapo.model

data class NotesModel(
    val id: Long = 0L,
    val title: String = "",
    val note: String = "",
)

val dummyNotesModel = listOf(
    NotesModel(
        id = 1L,
        title = "Note 1",
        note = "This is the first note."
    ),
    NotesModel(
        id = 2L,
        title = "Note 2",
        note = "This is the second note."
    ),
    NotesModel(
        id = 3L,
        title = "Note 3",
        note = "This is the third note."
    ),
    NotesModel(
        id = 4L,
        title = "Note 1",
        note = "This is the first note."
    ),
    NotesModel(
        id = 5L,
        title = "Note 2",
        note = "This is the second note."
    ),
    NotesModel(
        id = 6L,
        title = "Note 3",
        note = "This is the third note."
    )
)