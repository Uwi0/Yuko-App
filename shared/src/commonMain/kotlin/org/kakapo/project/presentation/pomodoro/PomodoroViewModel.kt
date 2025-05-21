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
            is PomodoroEvent.ChangePomodoroTime -> _uiState.update { it.copy(pomodoroTime = event.time) }
            is PomodoroEvent.ChangeFocusTime -> _uiState.update { it.copy(focusDuration = event.time) }
            is PomodoroEvent.ChangeStatus -> _uiState.update { it.copy(status = event.status) }
            is PomodoroEvent.ShowSheet -> _uiState.update { it.copy(showSheet = event.show) }
            is PomodoroEvent.ChangeShortRestTime -> _uiState.update { it.copy(shortRestDuration = event.time) }
            is PomodoroEvent.SetNumberOfCycles -> _uiState.update { it.copy(numberOfCycles = event.number) }
        }
    }
}