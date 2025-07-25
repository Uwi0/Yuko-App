package com.kakapo.model.date

import kotlinx.datetime.LocalDate

data class HorizontalCalendarArgs(
    val currentDay: LocalDate,
    val weeks: List<WeekModel>,
    val canScrollRight: Boolean,
    val canScrollLeft: Boolean
)
