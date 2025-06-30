package com.kakapo.model.reminder

import kotlinx.datetime.DayOfWeek

enum class ReminderDays(val title: String, val dayOfWeek: DayOfWeek) {
    SUNDAY("Sun", DayOfWeek.SUNDAY),
    MONDAY("Mon", DayOfWeek.MONDAY),
    TUESDAY("Tues", DayOfWeek.TUESDAY),
    WEDNESDAY("Wed", DayOfWeek.WEDNESDAY),
    THURSDAY("Thu", DayOfWeek.THURSDAY),
    FRIDAY("Fri", DayOfWeek.FRIDAY),
    SATURDAY("Sat", DayOfWeek.SATURDAY)
}

fun List<String>.toReminderDays(): List<ReminderDays> {
    return mapNotNull { title ->
        ReminderDays.entries.find { it.title == title }
    }
}