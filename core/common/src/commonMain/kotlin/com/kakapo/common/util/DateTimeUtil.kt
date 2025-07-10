package com.kakapo.common.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import kotlin.time.Clock
import kotlin.time.Instant

val todayAtMidnight: Long
    get() = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
        .atStartOfDayIn(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()

fun getEndOfMonthUnixTime(): Long {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val nextMonth = currentDate.plus(1, DateTimeUnit.MONTH)
    val firstDayOfNextMonth = LocalDate(nextMonth.year, nextMonth.month, 1)
    val lastDayOfCurrentMonth = firstDayOfNextMonth.minus(1, DateTimeUnit.DAY)
    val endOfMonthDateTime = lastDayOfCurrentMonth.atTime(23, 59, 59)
    val unixTime = endOfMonthDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    return unixTime
}

fun startDateAndEndDateOfMonth(
    month: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).month.number,
    currentYear: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
): Pair<Long, Long> {

    val startDate = LocalDate(currentYear, month, 1)
        .atStartOfDayIn(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()

    val daysInMonth = LocalDate(currentYear, month, 1)
        .plus(1, DateTimeUnit.MONTH)
        .minus(1, DateTimeUnit.DAY)
        .atTime(23, 59, 59, 999_999_999)
        .toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()

    return Pair(startDate, daysInMonth)
}

fun Long?.asDayClean(): Long {
    return this?.let { lastTime ->
        val formDate = Instant.fromEpochMilliseconds(lastTime)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        val toDate = Instant.fromEpochMilliseconds(todayAtMidnight)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        formDate.until(toDate, DateTimeUnit.DAY)
    } ?: 0
}

fun Long.toDateWith(format: String): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return localDateTime.toFormatedString(format)
}

expect fun LocalDateTime.toFormatedString(pattern: String): String