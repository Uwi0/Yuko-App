package org.kakapo.project.presentation.noteMenu.note

import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.NotesRepository
import com.kakapo.model.NotesModel
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("NoteViewModelKt")
class NoteViewModel(
    private val notesRepository: NotesRepository
): BaseViewModel<NoteState, NoteEffect, NoteEvent>(NoteState()) {

    private var noteId: Long = 0

    fun initData(noteId: Long) {
        this.noteId = noteId
        loadNoteBy(noteId)
    }

    override fun handleEvent(event: NoteEvent) {
        when(event) {
            NoteEvent.NavigateBack -> emit(NoteEffect.NavigateBack)
            NoteEvent.DeleteNote -> deleteNote()
            NoteEvent.EditNote -> emit(NoteEffect.TapToEditNote(noteId))
        }
    }

    private fun loadNoteBy(noteId: Long) = viewModelScope.launch {
        val onSuccess: (NotesModel) -> Unit = { note ->
            _uiState.value = _uiState.value.copy(note = note)
        }
        notesRepository.loadNoteById(noteId).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun deleteNote()  = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            emit(NoteEffect.NavigateBack)
        }
        notesRepository.deleteNoteById(noteId).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        emit(NoteEffect.ShowError(message))
    }
}