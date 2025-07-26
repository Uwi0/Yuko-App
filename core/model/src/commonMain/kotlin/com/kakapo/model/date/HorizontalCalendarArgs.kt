package com.kakapo.model.date

import kotlinx.datetime.LocalDate

data class HorizontalCalendarArgs(
    val currentDay: LocalDate,
    val allWeeks: List<WeekModel>,
    val week: WeekModel,
    val canScrollRight: Boolean,
    val canScrollLeft: Boolean
)
