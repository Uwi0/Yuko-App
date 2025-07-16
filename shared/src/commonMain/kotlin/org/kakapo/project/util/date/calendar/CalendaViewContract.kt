package org.kakapo.project.util.date.calendar

sealed class CalendarEvent {
    data class UpdateMonthWith(val index: Int): CalendarEvent()
}