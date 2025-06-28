package org.kakapo.project.presentation.noteMenu.notes

import androidx.lifecycle.viewModelScope
import com.kakapo.common.asResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.NotesRepository
import com.kakapo.model.NotesModel
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("NotesViewModelKt")
class NotesViewModel(
    private val notesRepository: NotesRepository
): BaseViewModel<NotesState, NotesEffect, NotesEvent>(NotesState()) {

    fun initData() {
        loadNotes()
    }

    override fun handleEvent(event: NotesEvent) {
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
}