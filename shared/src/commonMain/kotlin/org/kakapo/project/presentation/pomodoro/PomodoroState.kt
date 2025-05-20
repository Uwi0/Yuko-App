package org.kakapo.project.presentation.pomodoro

import com.kakapo.model.PomodoroStatus

data class PomodoroState(
    val currentTime: String = "",
    val status: PomodoroStatus = PomodoroStatus.BreakTime,
) {
    companion object {
        fun default() = PomodoroState()
    }
}

sealed class PomodoroEvent {
    data class ChangeTime(val time: String) : PomodoroEvent()
    data class ChangeStatus(val status: PomodoroStatus) : PomodoroEvent()
}