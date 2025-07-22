package com.kakapo.common.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.dateWithTimeIntervalSince1970

actual fun LocalDateTime.toFormatedString(pattern: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = pattern
    val epochMillis = this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    val date = NSDate.dateWithTimeIntervalSince1970(epochMillis / 1000.0)
    return dateFormatter.stringFromDate(date)
}