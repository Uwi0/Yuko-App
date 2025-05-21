package org.kakapo.project.presentation.pomodoro

import com.kakapo.model.PomodoroStatus

data class PomodoroState(
    val pomodoroTime: String = "",
    val focusDuration: Double = 30.0,
    val shortRestDuration: Double = 5.0,
    val numberOfCycles: Double = 3.0,
    val status: PomodoroStatus = PomodoroStatus.BreakTime,
    val showSheet: Boolean = false
) {
    companion object {
        fun default() = PomodoroState()
    }
}

sealed class PomodoroEvent {
    data class ChangePomodoroTime(val time: String) : PomodoroEvent()
    data class ChangeFocusTime(val time: Double) : PomodoroEvent()
    data class ChangeShortRestTime(val time: Double) : PomodoroEvent()
    data class SetNumberOfCycles(val number: Double) : PomodoroEvent()
    data class ChangeStatus(val status: PomodoroStatus) : PomodoroEvent()
    data class ShowSheet(val show: Boolean) : PomodoroEvent()
}