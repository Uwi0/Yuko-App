package org.kakapo.project.presentation.addNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("AddNoteViewModelKt")
class AddNoteViewModel : ViewModel() {

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<AddNoteEffect>()

    fun handleEvent(event: AddNoteEvent) {
        when (event) {
            AddNoteEvent.NavigateBack -> emit(AddNoteEffect.NavigateBack)
        }
    }

    private fun emit(effect: AddNoteEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}