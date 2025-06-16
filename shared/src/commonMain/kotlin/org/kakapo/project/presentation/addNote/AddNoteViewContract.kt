package org.kakapo.project.presentation.addNote

import com.kakapo.data.model.NotesParam

data class AddNoteState(
    val title: String = "",
    val note: String = ""
) {

    fun asNotesParam() = NotesParam(
        title = title,
        note = note
    )

    companion object {
        fun default() = AddNoteState()
    }
}

sealed class AddNoteEffect {
    data object NavigateBack: AddNoteEffect()
    data object SuccessSaveNote: AddNoteEffect()
}

sealed class AddNoteEvent {
    data class TitleChanged(val title: String): AddNoteEvent()
    data class NoteChanged(val note: String): AddNoteEvent()
    data object NavigateBack: AddNoteEvent()
    data object SaveNote: AddNoteEvent()
}