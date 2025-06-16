package org.kakapo.project.presentation.addNote

data class AddNoteState(
    val title: String = "",
    val note: String = ""
) {

    companion object {
        fun default() = AddNoteState()
    }
}

sealed class AddNoteEffect {
    data object NavigateBack: AddNoteEffect()
}

sealed class AddNoteEvent {
    data class TitleChanged(val title: String): AddNoteEvent()
    data class NoteChanged(val note: String): AddNoteEvent()
    data object NavigateBack: AddNoteEvent()
}