package org.kakapo.project.presentation.noteMenu.note

import com.kakapo.model.NotesModel

data class NoteState(
    val note: NotesModel = NotesModel()
) {
    companion object {
        fun default() = NoteState()
    }
}

sealed class NoteEffect {
    data object NavigateBack : NoteEffect()
    data class ShowError(val message: String) : NoteEffect()
    data class TapToEditNote(val noteId: Long) : NoteEffect()
}

sealed class NoteEvent {
    data object NavigateBack : NoteEvent()
    data object DeleteNote : NoteEvent()
    data object EditNote : NoteEvent()
}