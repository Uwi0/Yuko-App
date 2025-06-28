package org.kakapo.project.presentation.noteMenu.addNote

import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.NotesRepository
import com.kakapo.model.NotesModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("AddNoteViewModelKt")
class AddNoteViewModel(
    private val notesRepository: NotesRepository
) : BaseViewModel<AddNoteState, AddNoteEffect, AddNoteEvent>(AddNoteState()) {

    private var noteId: Long = 0

    fun initData(noteId: Long) {
        this.noteId = noteId
        if(noteId != 0L) loadNoteBy(noteId)
    }

    override fun handleEvent(event: AddNoteEvent) {
        when (event) {
            AddNoteEvent.NavigateBack -> emit(AddNoteEffect.NavigateBack)
            is AddNoteEvent.NoteChanged -> _uiState.update { it.copy(note = event.note) }
            is AddNoteEvent.TitleChanged -> _uiState.update { it.copy(title = event.title) }
            AddNoteEvent.SaveNote -> saveNote()
        }
    }

    private fun loadNoteBy(noteId: Long) = viewModelScope.launch {
        val onSuccess: (NotesModel) -> Unit = { note ->
            _uiState.update { it.copy(note = note) }
        }
        notesRepository.loadNoteById(noteId).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun saveNote() = viewModelScope.launch {
        val notesParam = uiState.value.asNotesParam(noteId)
        notesRepository.saveNote(notesParam).fold(
            onSuccess = { emit(AddNoteEffect.SuccessSaveNote) },
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        emit(AddNoteEffect.ShowError(message))
    }
}