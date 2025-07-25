package org.kakapo.project.util.date.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus

fun startOfWeek(date: LocalDate): LocalDate {
    val dow = date.dayOfWeek.isoDayNumber
    return date.minus((dow % 7).toLong(), DateTimeUnit.DAY)
}

fun lastDayOfLastWeekInCurrentMonth(currentDate: LocalDate): LocalDate {
    val lastDayOfMonth = currentDate
        .plus(1, DateTimeUnit.MONTH)
        .minus(currentDate.day, DateTimeUnit.DAY)

    val startOfLastWeek = startOfWeek(lastDayOfMonth)
    return startOfLastWeek.plus(6, DateTimeUnit.DAY)
}