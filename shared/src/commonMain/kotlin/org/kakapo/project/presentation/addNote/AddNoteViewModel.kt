package org.kakapo.project.presentation.addNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.data.repository.base.NotesRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("AddNoteViewModelKt")
class AddNoteViewModel(
    private val notesRepository: NotesRepository
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(AddNoteState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<AddNoteEffect>()

    fun handleEvent(event: AddNoteEvent) {
        when (event) {
            AddNoteEvent.NavigateBack -> emit(AddNoteEffect.NavigateBack)
            is AddNoteEvent.NoteChanged -> _uiState.update { it.copy(note = event.note) }
            is AddNoteEvent.TitleChanged -> _uiState.update { it.copy(title = event.title) }
            AddNoteEvent.SaveNote -> saveNote()
        }
    }

    private fun saveNote() = viewModelScope.launch {
        val notesParam = uiState.value.asNotesParam()
        notesRepository.saveNote(notesParam).fold(
            onSuccess = { emit(AddNoteEffect.SuccessSaveNote) },
            onFailure = { Logger.e(it) { "Failed to save note" } }
        )
    }

    private fun emit(effect: AddNoteEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}