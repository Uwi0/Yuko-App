package org.kakapo.project.util.date.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus

fun startOfWeek(date: LocalDate): LocalDate {
    val dow = date.dayOfWeek.isoDayNumber
    return date.minus((dow % 7).toLong(), DateTimeUnit.DAY)
}