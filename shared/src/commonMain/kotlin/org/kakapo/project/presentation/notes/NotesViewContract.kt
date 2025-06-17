package org.kakapo.project.presentation.notes

import com.kakapo.model.NotesModel

data class NotesState(
    val notes: List<NotesModel> = emptyList()
) {
    companion object {
        fun default() = NotesState()
    }
}

sealed class NotesEffect {
    data object NavigateBack : NotesEffect()
    data object TapToAddNote : NotesEffect()
    data object TapToNote : NotesEffect()
    data class ShowError(val message: String) : NotesEffect()
}

sealed class NotesEvent {
    data object NavigateBack : NotesEvent()
    data object TapToAddNote : NotesEvent()
    data object TapToNote : NotesEvent()
}