package org.kakapo.project.presentation.pomodoroMenu.pomodoro

import com.kakapo.data.model.PomodoroSessionParam
import com.kakapo.model.pomodoro.SessionType
import com.kakapo.model.pomodoro.SessionSettingsModel
import org.kakapo.project.presentation.pomodoroMenu.pomodoro.ext.toFormatMinutesAndSeconds
import kotlin.time.Clock

data class PomodoroState(
    val pointEarned: Long = 0,
    val pomodoroTime: String = "00:00",
    val countDownTime: String = "5",
    val focusDuration: Double = 30.0,
    val shortRestDuration: Double = 5.0,
    val numberOfCycles: Double = 3.0,
    val session: SessionType = SessionType.Start,
    val showAlert: Boolean = false,
    val showSheet: Boolean = false,
    val screenState: PomodoroScreenState = PomodoroScreenState.Pomodoro,
) {

    val durationFocusInMinutes: Int
        get() = (focusDuration * MINUTES).toInt()

    val durationRestInMinutes: Int
        get() = (shortRestDuration * MINUTES).toInt()

    val showSuccessPage: Boolean
        get() = screenState == PomodoroScreenState.SuccessPage

    val showFailPage: Boolean
        get() = screenState == PomodoroScreenState.FailPage

    val showBreakSuccessPage: Boolean
        get() = screenState == PomodoroScreenState.BreakSuccess

    fun resetScreenState(): PomodoroState {
        return this.copy(screenState = PomodoroScreenState.Pomodoro)
    }

    fun setPomodoro(): PomodoroState {
        val time = durationFocusInMinutes.toInt().toFormatMinutesAndSeconds()
        return this.copy(pomodoroTime = time, showSheet = false)
    }

    fun getSettings(): SessionSettingsModel = SessionSettingsModel(
            focusDuration = focusDuration,
            restDuration = shortRestDuration,
            cycleCount = numberOfCycles
        )

    fun initialState(screenState: PomodoroScreenState = PomodoroScreenState.Pomodoro): PomodoroState {
        return this.copy(
            pomodoroTime = durationFocusInMinutes.toFormatMinutesAndSeconds(),
            session = SessionType.Start,
            screenState = screenState,
            showSheet = false
        )
    }

    fun updateFromSettings(settings: SessionSettingsModel): PomodoroState {
        val pomodoroTime = settings.focusDuration * MINUTES
        return this.copy(
            pomodoroTime = pomodoroTime.toInt().toFormatMinutesAndSeconds(),
            focusDuration = settings.focusDuration,
            shortRestDuration = settings.restDuration,
            numberOfCycles = settings.cycleCount
        )
    }

    fun getPomodoroSessionParam(startTime: Long, isComplete: Boolean) : PomodoroSessionParam {
        return PomodoroSessionParam(
            startTime = startTime,
            endTime = Clock.System.now().epochSeconds,
            duration = focusDuration.toLong(),
            isCompleted = isComplete
        )
    }

    companion object {
        fun default() = PomodoroState()
        const val MINUTES = 1
    }
}

enum class PomodoroScreenState {
    Pomodoro,
    SuccessPage,
    FailPage,
    BreakSuccess
}

sealed class PomodoroEffect {
    data class ShowError(val message: String): PomodoroEffect()
    data class StartPomodoro(val time: Int): PomodoroEffect()
    data object CancelCountdown: PomodoroEffect()
    data object CancelPomodoro: PomodoroEffect()
    data object StartBreak: PomodoroEffect()
    data object FinishPomodoro: PomodoroEffect()
}

sealed class PomodoroEvent {
    data class ChangePomodoroTime(val time: String) : PomodoroEvent()
    data class ChangeFocusTime(val time: Double) : PomodoroEvent()
    data class ChangeShortRestTime(val time: Double) : PomodoroEvent()
    data class SetNumberOfCycles(val number: Double) : PomodoroEvent()
    data class ChangeStatus(val status: SessionType) : PomodoroEvent()
    data class ShowSheet(val show: Boolean) : PomodoroEvent()
    data class SaveProgress(val isSuccess: Boolean): PomodoroEvent()
    data class ChangeCountDownTime(val time: String): PomodoroEvent()
    data class ShowAlert(val shown: Boolean) : PomodoroEvent()
    data object SaveSettings: PomodoroEvent()
    data object DoActionButton: PomodoroEvent()
    data object FinishPomodoro: PomodoroEvent()
    data object StartBreak: PomodoroEvent()
    data object ContinuePomodoro: PomodoroEvent()
    data object RetryPomodoro: PomodoroEvent()
    data object FinishBreak: PomodoroEvent()
}