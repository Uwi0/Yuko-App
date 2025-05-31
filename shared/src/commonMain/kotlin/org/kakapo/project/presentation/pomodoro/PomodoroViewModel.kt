package org.kakapo.project.presentation.pomodoro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.PomodoroSessionRepository
import com.kakapo.model.WorkState
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
import kotlinx.datetime.Clock

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

    private var startTime = 0L

    fun initData() {
        loadSessionSettings()
        loadTotalPointEarned()
    }

    fun handleEvent(event: PomodoroEvent) {
        when (event) {
            is PomodoroEvent.ChangePomodoroTime -> _uiState.update { it.copy(pomodoroTime = event.time) }
            is PomodoroEvent.ChangeFocusTime -> _uiState.update { it.copy(focusDuration = event.time) }
            is PomodoroEvent.ChangeStatus -> _uiState.update { it.copy(status = event.status) }
            is PomodoroEvent.ShowSheet -> _uiState.update { it.copy(showSheet = event.show) }
            is PomodoroEvent.ChangeShortRestTime -> _uiState.update { it.copy(shortRestDuration = event.time) }
            is PomodoroEvent.SetNumberOfCycles -> _uiState.update { it.copy(numberOfCycles = event.number) }
            is PomodoroEvent.SaveProgress -> saveSessionProgress(event.isSuccess)
            is PomodoroEvent.ChangeCountDownTime -> _uiState.update { it.copy(countDownTime = event.time) }
            is PomodoroEvent.ShowAlert -> _uiState.update { it.copy(showAlert = event.shown) }
            PomodoroEvent.StartPomodoro -> startPomodoro()
            PomodoroEvent.SaveSettings -> saveSessionSettings()
            PomodoroEvent.CancelTimer -> cancelPomodoro()
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

    private fun loadTotalPointEarned() = viewModelScope.launch {
        val onSuccess: (Long) -> Unit = { point ->
            _uiState.update { it.copy(pointEarned = point) }
        }

        sessionRepository.loadTotalPoints().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun saveSessionSettings() = viewModelScope.launch {
        val settings = _uiState.value.getSettings()
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.setPomodoro() }
        }
        sessionRepository.saveSessionSettings(settings).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun startPomodoro() {
        val duration = _uiState.value.focusDuration.toInt() * 60
        startTime = Clock.System.now().epochSeconds
        _uiState.update { it.copy(status = WorkState.CountDown) }
        emit(PomodoroEffect.StartPomodoro(duration))
    }

    private fun saveSessionProgress(isSuccess: Boolean) = viewModelScope.launch {
        val param = _uiState.value.getPomodoroSessionParam(startTime, isSuccess)
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.copy(status = WorkState.BreakTime) }
            loadTotalPointEarned()
        }

        sessionRepository.saveSessionProgress(param).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun cancelPomodoro() = viewModelScope.launch {
        if(uiState.value.status == WorkState.CountDown){
            emit(PomodoroEffect.CancelTimer)
            _uiState.update { it.copy(status = WorkState.BreakTime) }
        } else {
            _uiState.update { it.copy(showAlert = true) }
        }
    }

    private fun handleError(throwable: Throwable?) {
        emit(PomodoroEffect.ShowError(throwable?.message ?: "Unknown Error"))
    }

    private fun emit(effect: PomodoroEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}