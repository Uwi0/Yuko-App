package org.kakapo.project.presentation.addNote

import com.kakapo.data.model.NotesParam
import com.kakapo.model.NotesModel

data class AddNoteState(
    val title: String = "",
    val note: String = ""
) {

    fun asNotesParam(id: Long) = NotesParam(
        id = id,
        title = title,
        note = note
    )

    fun copy(note: NotesModel) = copy(
        title = note.title,
        note = note.note
    )

    companion object {
        fun default() = AddNoteState()
    }
}

sealed class AddNoteEffect {
    data object NavigateBack: AddNoteEffect()
    data object SuccessSaveNote: AddNoteEffect()
    data class ShowError(val message: String): AddNoteEffect()
}

sealed class AddNoteEvent {
    data class TitleChanged(val title: String): AddNoteEvent()
    data class NoteChanged(val note: String): AddNoteEvent()
    data object NavigateBack: AddNoteEvent()
    data object SaveNote: AddNoteEvent()
}