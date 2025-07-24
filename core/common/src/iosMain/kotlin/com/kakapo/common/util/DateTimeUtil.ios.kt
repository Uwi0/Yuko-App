package com.kakapo.common.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.dateWithTimeIntervalSince1970

actual fun LocalDateTime.fromEpochMillsToFormatedString(pattern: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = pattern
    val epochMillis = this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    val date = NSDate.dateWithTimeIntervalSince1970(epochMillis / 1000.0)
    return dateFormatter.stringFromDate(date)
}

actual fun LocalDate.fromEpochDayToFormattedString(pattern: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = pattern

    val epochDays = this.toEpochDays()
    val timeIntervalSince1970 = epochDays * 24 * 60 * 60.0
    val date = NSDate.dateWithTimeIntervalSince1970(timeIntervalSince1970)

    return dateFormatter.stringFromDate(date)
}