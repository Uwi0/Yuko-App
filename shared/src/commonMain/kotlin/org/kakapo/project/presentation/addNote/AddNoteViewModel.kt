package org.kakapo.project.presentation.addNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class AddNoteViewModel : ViewModel() {

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
        }
    }

    private fun emit(effect: AddNoteEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}