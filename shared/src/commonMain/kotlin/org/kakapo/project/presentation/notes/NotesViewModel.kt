package org.kakapo.project.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("NotesViewModelKt")
class NotesViewModel(): ViewModel() {

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<NotesEffect>()

    fun handleEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.TapToAddNote -> emit(NotesEffect.TapToAddNote)
            is NotesEvent.TapToNote -> emit(NotesEffect.TapToNote)
        }
    }

    private fun emit(effect: NotesEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}