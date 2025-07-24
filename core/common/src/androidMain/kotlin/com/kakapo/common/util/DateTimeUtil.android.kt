package com.kakapo.common.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun LocalDateTime.fromEpochMillsToFormatedString(pattern: String): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    val date = Date(this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds())
    return formatter.format(date)
}

actual fun LocalDate.fromEpochDayToFormattedString(pattern: String): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())

    val epochDays = this.toEpochDays()
    val epochMillis = epochDays * 24 * 60 * 60 * 1000L
    val date = Date(epochMillis)

    return formatter.format(date)
}
