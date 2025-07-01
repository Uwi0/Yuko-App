package com.kakapo.common.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
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