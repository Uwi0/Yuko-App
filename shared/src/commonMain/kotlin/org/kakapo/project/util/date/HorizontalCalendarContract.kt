package org.kakapo.project.util.date

import kotlinx.datetime.LocalDate

sealed class HorizontalCalendarEffect {
    data class WeekChanged(val weeks: List<LocalDate>): HorizontalCalendarEffect()
}

sealed class HorizontalCalendarEvent {
    data class UpdateWeekWith(val index: Int): HorizontalCalendarEvent()
}