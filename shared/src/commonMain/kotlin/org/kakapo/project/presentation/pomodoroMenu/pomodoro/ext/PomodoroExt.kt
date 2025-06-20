package org.kakapo.project.presentation.pomodoroMenu.pomodoro.ext

fun Int.toFormatMinutesAndSeconds(): String {
    val minutes = this / 60
    val secondsPart = this % 60
    return "${minutes.toString().padStart(2, '0')}:${secondsPart.toString().padStart(2, '0')}"
}