package org.kakapo.project.presentation.addNote

sealed class AddNoteEffect {
    data object NavigateBack: AddNoteEffect()
}

sealed class AddNoteEvent {
    data object NavigateBack: AddNoteEvent()
}