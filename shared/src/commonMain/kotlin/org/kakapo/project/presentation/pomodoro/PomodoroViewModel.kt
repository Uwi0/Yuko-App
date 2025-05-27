package org.kakapo.project.presentation.pomodoro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.PomodoroSessionRepository
import com.kakapo.model.SessionSettingsModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.native.ObjCName
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@ObjCName("PomodoroViewModelKT")
class PomodoroViewModel(
    private val sessionRepository: PomodoroSessionRepository,
) : ViewModel() {

    @NativeCoroutinesState
    val uiState: StateFlow<PomodoroState> get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(PomodoroState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<PomodoroEffect>()

    fun initData() {
        loadSessionSettings()
    }

    fun handleEvent(event: PomodoroEvent) {
        when (event) {
            is PomodoroEvent.ChangePomodoroTime -> _uiState.update { it.copy(pomodoroTime = event.time) }
            is PomodoroEvent.ChangeFocusTime -> _uiState.update { it.copy(focusDuration = event.time) }
            is PomodoroEvent.ChangeStatus -> _uiState.update { it.copy(status = event.status) }
            is PomodoroEvent.ShowSheet -> _uiState.update { it.copy(showSheet = event.show) }
            is PomodoroEvent.ChangeShortRestTime -> _uiState.update { it.copy(shortRestDuration = event.time) }
            is PomodoroEvent.SetNumberOfCycles -> _uiState.update { it.copy(numberOfCycles = event.number) }
            PomodoroEvent.StartPomodoro -> startPomodoro()
            PomodoroEvent.SaveSettings -> saveSessionSettings()
        }
    }

    private fun loadSessionSettings() = viewModelScope.launch {
        val onSuccess: (SessionSettingsModel) -> Unit = { settings ->
            _uiState.update { it.updateFromSettings(settings) }
        }

        sessionRepository.loadSessionSettings().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun saveSessionSettings() = viewModelScope.launch {
        val settings = _uiState.value.getSettings()
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.copy(showSheet = false) }
        }
        sessionRepository.saveSessionSettings(settings).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun startPomodoro() {
        val duration = _uiState.value.focusDuration.toInt() * 60
        _uiState.update { it.startPomodoro() }
        emit(PomodoroEffect.StartPomodoro(duration))
    }

    private fun handleError(throwable: Throwable?) {
        emit(PomodoroEffect.ShowError(throwable?.message ?: "Unknown Error"))
    }

    private fun emit(effect: PomodoroEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}