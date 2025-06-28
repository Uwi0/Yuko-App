package org.kakapo.project.presentation.pomodoroMenu.pomodoro

import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.PomodoroSessionRepository
import com.kakapo.model.SessionSettingsModel
import com.kakapo.model.SessionType
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName
import kotlin.time.Clock

@ObjCName("PomodoroViewModelKT")
class PomodoroViewModel(
    private val sessionRepository: PomodoroSessionRepository,
) : BaseViewModel<PomodoroState, PomodoroEffect, PomodoroEvent>(PomodoroState()) {

    private var startTime = 0L

    fun initData() {
        loadSessionSettings()
        loadTotalPointEarned()
    }

    override fun handleEvent(event: PomodoroEvent) {
        when (event) {
            is PomodoroEvent.ChangePomodoroTime -> _uiState.update { it.copy(pomodoroTime = event.time) }
            is PomodoroEvent.ChangeFocusTime -> _uiState.update { it.copy(focusDuration = event.time) }
            is PomodoroEvent.ChangeStatus -> _uiState.update { it.copy(session = event.status) }
            is PomodoroEvent.ShowSheet -> _uiState.update { it.copy(showSheet = event.show) }
            is PomodoroEvent.ChangeShortRestTime -> _uiState.update { it.copy(shortRestDuration = event.time) }
            is PomodoroEvent.SetNumberOfCycles -> _uiState.update { it.copy(numberOfCycles = event.number) }
            is PomodoroEvent.SaveProgress -> saveSessionProgress(event.isSuccess)
            is PomodoroEvent.ChangeCountDownTime -> _uiState.update { it.copy(countDownTime = event.time) }
            is PomodoroEvent.ShowAlert -> _uiState.update { it.copy(showAlert = event.shown) }
            PomodoroEvent.SaveSettings -> saveSessionSettings()
            PomodoroEvent.DoActionButton -> doActionButton()
            PomodoroEvent.ContinuePomodoro -> _uiState.update { it.resetScreenState() }
            PomodoroEvent.FinishPomodoro -> finishPomodoro()
            PomodoroEvent.StartBreak -> startBreak()
            PomodoroEvent.RetryPomodoro -> retryPomodoro()
            PomodoroEvent.FinishBreak -> _uiState.update { it.copy(screenState = PomodoroScreenState.BreakSuccess) }
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
            emit(PomodoroEffect.CancelPomodoro)
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

    private fun doActionButton() {
        val session = _uiState.value.session
        when (session) {
            SessionType.Start -> startPomodoro()
            SessionType.Cancel -> cancelPomodoro()
            SessionType.GiveUp -> _uiState.update { it.copy(showAlert = true) }
            SessionType.BreakTime -> emit(PomodoroEffect.CancelPomodoro)
        }
    }

    private fun startPomodoro() {
        val duration = _uiState.value.durationFocusInMinutes
        startTime = Clock.System.now().epochSeconds
        _uiState.update { it.copy(session = SessionType.Cancel) }
        emit(PomodoroEffect.StartPomodoro(duration))
    }

    private fun saveSessionProgress(isSuccess: Boolean) = viewModelScope.launch {
        val param = _uiState.value.getPomodoroSessionParam(startTime, isSuccess)
        val screenState = if (isSuccess) PomodoroScreenState.SuccessPage
        else PomodoroScreenState.FailPage
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.initialState(screenState = screenState) }
            loadTotalPointEarned()
        }

        sessionRepository.saveSessionProgress(param).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun cancelPomodoro() = viewModelScope.launch {
        emit(PomodoroEffect.CancelCountdown)
        _uiState.update { it.copy(session = SessionType.Start) }
    }

    private fun startBreak() {
        _uiState.update { it.resetScreenState() }
        _uiState.update { it.copy(session = SessionType.BreakTime) }
        emit(PomodoroEffect.StartBreak)
    }

    private fun retryPomodoro() {
        _uiState.update { it.resetScreenState() }
        startPomodoro()
    }

    private fun finishPomodoro() {
        _uiState.update { it.resetScreenState() }
        emit(PomodoroEffect.FinishPomodoro)
    }

    private fun handleError(throwable: Throwable?) {
        emit(PomodoroEffect.ShowError(throwable?.message ?: "Unknown Error"))
    }
}