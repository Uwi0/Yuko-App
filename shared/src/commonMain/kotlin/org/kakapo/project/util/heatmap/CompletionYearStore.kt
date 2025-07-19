package org.kakapo.project.util.heatmap

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
        completionData: Map<LocalDate, Int>
    ): CompletionYearModel {
        val startDate = getYearStartDate(year)
        val endDate = getYearEndDate(year)

        val weeks = generateWeeks(startDate, endDate, year, completionData)
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
        completionData: Map<LocalDate, Int>
    ): List<CompletionWeekModel> {
        return generateSequence(startDate) { currentDate ->
            currentDate.plus(7, DateTimeUnit.DAY).takeIf { it <= endDate }
        }.map { weekStart ->
            val weekDates = generateWeekDates(weekStart, endDate, targetYear, completionData)
            CompletionWeekModel(days = weekDates)
        }.toList()
    }

    private fun generateWeekDates(
        weekStart: LocalDate,
        yearEnd: LocalDate,
        targetYear: Int,
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
                        level = calculateLevel(count)
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

    private fun calculateLevel(count: Int): Int {
        return when {
            count == 0 -> 0
            count <= 3 -> 1
            count <= 6 -> 2
            count <= 9 -> 3
            else -> 4
        }
    }
}