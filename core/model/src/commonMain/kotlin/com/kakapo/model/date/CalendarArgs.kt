package com.kakapo.model.date

import kotlinx.datetime.LocalDate

data class CalendarArgs(
    val currentDate: LocalDate,
    val months: List<MonthModel>,
    val canScrollRight: Boolean,
    val canScrollLeft: Boolean
)
