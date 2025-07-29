package org.kakapo.project.util.heatmap

import co.touchlab.kermit.Logger
import com.kakapo.model.heatmap.CompletionDayModel
import com.kakapo.model.heatmap.CompletionWeekModel
import com.kakapo.model.heatmap.CompletionYearModel
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus

class CompletionYearStore {

    fun generateCompletionYear(
        year: Int,
        targetFrequency: Int,
        completionData: Map<LocalDate, Int>
    ): CompletionYearModel {
        val startDate = getYearStartDate(year)
        val endDate = getYearEndDate(year)
        Logger.d("generateCompletionYear $targetFrequency, $completionData")
        val weeks = generateWeeks(
            startDate, endDate,
            year,
            targetFrequency,
            completionData
        )
        val totalCompletions = completionData.values.sum()

        return CompletionYearModel(
            year = year,
            weeks = weeks,
            totalCompletions = totalCompletions
        )
    }

    private fun generateWeeks(
        startDate: LocalDate,
        endDate: LocalDate,
        targetYear: Int,
        targetFrequency: Int,
        completionData: Map<LocalDate, Int>
    ): List<CompletionWeekModel> {
        return generateSequence(startDate) { currentDate ->
            currentDate.plus(7, DateTimeUnit.DAY).takeIf { it <= endDate }
        }.map { weekStart ->
            val weekDates = generateWeekDates(weekStart, endDate, targetYear, targetFrequency, completionData)
            CompletionWeekModel(days = weekDates)
        }.toList()
    }

    private fun generateWeekDates(
        weekStart: LocalDate,
        yearEnd: LocalDate,
        targetYear: Int,
        targetFrequency: Int,
        completionData: Map<LocalDate, Int>
    ): List<CompletionDayModel> {
        return (0..6).map { dayOffset ->
            val currentDate = weekStart.plus(dayOffset, DateTimeUnit.DAY)

            when {
                currentDate > yearEnd -> CompletionDayModel.Empty
                currentDate.year != targetYear -> CompletionDayModel.Empty
                else -> {
                    val count = completionData[currentDate] ?: 0
                    CompletionDayModel.Day(
                        date = currentDate,
                        count = count,
                        level = calculateLevel(count, targetFrequency)
                    )
                }
            }
        }
    }

    private fun getYearStartDate(year: Int): LocalDate {
        val januaryFirst = LocalDate(year, 1, 1)
        val dayOfWeek = januaryFirst.dayOfWeek.isoDayNumber % 7

        return januaryFirst.minus(dayOfWeek, DateTimeUnit.DAY)
    }

    private fun getYearEndDate(year: Int): LocalDate {
        val decemberLast = LocalDate(year, 12, 31)
        val dayOfWeek = decemberLast.dayOfWeek.isoDayNumber % 7

        return decemberLast.plus(6 - dayOfWeek, DateTimeUnit.DAY)
    }

    private fun calculateLevel(count: Int, targetFrequency: Int): Int {
        if (count == 0 || targetFrequency == 0) return 0

        val percent = (count.toDouble() / targetFrequency).coerceIn(0.0, 1.0)

        return when {
            percent == 0.0 -> 0
            percent <= 0.25 -> 1
            percent <= 0.5 -> 2
            percent <= 0.75 -> 3
            else -> 4
        }
    }
}