package com.kakapo.model.habit

import com.kakapo.common.util.dayToDateWith
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random
import kotlin.time.Clock

data class GoodHabitModel(
    val name: String = "",
    val description: String = "",
    val missedCount: Int = 0,
    val currentStreak: Int = 0,
    val totalComplete: Int = 0,
    val bestStreak: Int = 0,
    val completionThisMonth: Int = 0,
    val startDate: Long = 0,
    val completionType: CompletionType = CompletionType.Single,
    val targetFrequency: Int = 0,
    val calendarMap: Map<Long, Int> = emptyMap(),
) {
    val formattedStartDate: String get () {
        return startDate.dayToDateWith(format = "dd MMM yyyy")
    }

    fun isCompleteOnDate(date: Long): Boolean {
        return when(completionType) {
            CompletionType.Single -> calendarMap[date] == 1
            CompletionType.Frequency -> (calendarMap[date] ?: 0) >= targetFrequency
        }
    }

    fun getTargetData(date: Long): Float {
        return when (completionType) {
            CompletionType.Single -> if (calendarMap[date] == 1) 1f else 0f
            CompletionType.Frequency -> {
                val completed = calendarMap[date] ?: 0
                (completed.toFloat() / targetFrequency).coerceAtMost(1f)
            }
        }
    }
}

val dummyGoodHabit: GoodHabitModel get() {
    val timeZone = TimeZone.currentSystemDefault()
    val today = Clock.System.now().toLocalDateTime(timeZone).date

    val startDate = today.minus(60, DateTimeUnit.DAY)
        .atStartOfDayIn(timeZone)
        .toEpochMilliseconds()

    val calendarMap = (0 until 30).associate { i ->
        val day = today.minus(i, DateTimeUnit.DAY)
            .atStartOfDayIn(timeZone)
            .toEpochMilliseconds()
        day to 1
    }

    return GoodHabitModel(
        name = "Drink Water",
        description = "8 glasses daily",
        missedCount = 12,
        currentStreak = 6,
        totalComplete = 96,
        bestStreak = 21,
        completionThisMonth = 18,
        startDate = 0,
        calendarMap = calendarMap
    )
}
