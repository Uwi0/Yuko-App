package org.kakapo.project.presentation.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.NotesRepository
import com.kakapo.model.NotesModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("NoteViewModelKt")
class NoteViewModel(
    private val notesRepository: NotesRepository
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(NoteState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<NoteEffect>()

    private var noteId: Long = 0

    fun initData(noteId: Long) {
        this.noteId = noteId
        loadNoteBy(noteId)
    }

    fun handleEvent(event: NoteEvent) {
        when(event) {
            NoteEvent.NavigateBack -> emit(NoteEffect.NavigateBack)
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

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        emit(NoteEffect.ShowError(message))
    }

    private fun emit(effect: NoteEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}