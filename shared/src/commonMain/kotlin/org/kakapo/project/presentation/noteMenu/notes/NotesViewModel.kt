package org.kakapo.project.presentation.noteMenu.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asResult
import com.kakapo.common.subscribe
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

@ObjCName("NotesViewModelKt")
class NotesViewModel(
    private val notesRepository: NotesRepository
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(NotesState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<NotesEffect>()

    fun initData() {
        loadNotes()
    }

    fun handleEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.TapToNote -> emit(NotesEffect.TapToNote(event.noteId))
            NotesEvent.TapToAddNote -> emit(NotesEffect.TapToAddNote)
            NotesEvent.NavigateBack -> emit(NotesEffect.NavigateBack)
        }
    }

    private fun loadNotes() = viewModelScope.launch {
        val onSuccess: (List<NotesModel>) -> Unit = { notes ->
            _uiState.value = _uiState.value.copy(notes = notes)
        }
        notesRepository.loadNotes().asResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        emit(NotesEffect.ShowError(message))
    }

    private fun emit(effect: NotesEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}