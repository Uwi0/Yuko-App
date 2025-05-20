package org.kakapo.project.presentation.pomodoro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.native.ObjCName
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.update

@ObjCName("PomodoroViewModelKT")
class PomodoroViewModel : ViewModel(){

    @NativeCoroutinesState
    val uiState: StateFlow<PomodoroState> get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(PomodoroState())

    fun handleEvent(event: PomodoroEvent) {
        when(event) {
            is PomodoroEvent.ChangeTime -> _uiState.update { it.copy(currentTime = event.time) }
            is PomodoroEvent.ChangeStatus -> _uiState.update { it.copy(status = event.status) }
        }
    }
}