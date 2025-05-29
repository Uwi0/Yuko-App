package org.kakapo.project.presentation.pomodoro

import com.kakapo.data.model.PomodoroSessionParam
import com.kakapo.model.WorkState
import com.kakapo.model.SessionSettingsModel
import kotlinx.datetime.Clock
import org.kakapo.project.presentation.pomodoro.ext.toFormatMinutesAndSeconds

data class PomodoroState(
    val pointEarned: Long = 0,
    val pomodoroTime: String = "00:00",
    val countDownTime: String = "5",
    val focusDuration: Double = 30.0,
    val shortRestDuration: Double = 5.0,
    val numberOfCycles: Double = 3.0,
    val status: WorkState = WorkState.BreakTime,
    val showSheet: Boolean = false
) {

    fun setPomodoro(): PomodoroState {
        val durationInMinutes = focusDuration * 60
        val time = durationInMinutes.toInt().toFormatMinutesAndSeconds()
        return this.copy(pomodoroTime = time, showSheet = false)
    }

    fun getSettings(): SessionSettingsModel = SessionSettingsModel(
            focusDuration = focusDuration,
            restDuration = shortRestDuration,
            cycleCount = numberOfCycles
        )


    fun updateFromSettings(settings: SessionSettingsModel): PomodoroState {
        val pomodoroTime = settings.focusDuration * 60
        return this.copy(
            pomodoroTime = pomodoroTime.toInt().toFormatMinutesAndSeconds(),
            focusDuration = settings.focusDuration,
            shortRestDuration = settings.restDuration,
            numberOfCycles = settings.cycleCount
        )
    }

    fun getPomodoroSessionParam(startTime: Long, isComplete: Boolean) : PomodoroSessionParam {
        val pointEarned = if (isComplete) (focusDuration / 5).toLong() else -5
        return PomodoroSessionParam(
            startTime = startTime,
            endTime = Clock.System.now().epochSeconds,
            duration = focusDuration.toLong(),
            pointEarned = pointEarned,
            isCompleted = isComplete
        )
    }

    companion object {
        fun default() = PomodoroState()
    }
}

sealed class PomodoroEffect {
    data class ShowError(val message: String): PomodoroEffect()
    data class StartPomodoro(val time: Int): PomodoroEffect()
    data object CancelTimer: PomodoroEffect()
}

sealed class PomodoroEvent {
    data class ChangePomodoroTime(val time: String) : PomodoroEvent()
    data class ChangeFocusTime(val time: Double) : PomodoroEvent()
    data class ChangeShortRestTime(val time: Double) : PomodoroEvent()
    data class SetNumberOfCycles(val number: Double) : PomodoroEvent()
    data class ChangeStatus(val status: WorkState) : PomodoroEvent()
    data class ShowSheet(val show: Boolean) : PomodoroEvent()
    data object SaveSettings: PomodoroEvent()
    data object StartPomodoro : PomodoroEvent()
    data class SaveProgress(val isSuccess: Boolean): PomodoroEvent()
    data class ChangeCountDownTime(val time: String): PomodoroEvent()
    data object CancelTimer: PomodoroEvent()
}