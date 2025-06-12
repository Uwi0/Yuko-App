package org.kakapo.project.presentation.notes

sealed class NotesEffect {
    data object TapToAddNote : NotesEffect()
    data object TapToNote : NotesEffect()
}

sealed class NotesEvent {
    data object TapToAddNote : NotesEvent()
    data object TapToNote : NotesEvent()
}