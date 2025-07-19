package org.kakapo.project.util.heatmap

import com.kakapo.model.heatmap.CompletionYearModel
import com.kakapo.model.heatmap.MonthInfoModel
import kotlinx.datetime.number

fun calculateMonthPositions(
    completionYear: CompletionYearModel,
    daySize: Float,
    daySpacing: Float
): List<MonthInfoModel> {

    val monthTransitions = completionYear.weeks
        .withIndex()
        .mapNotNull { (weekIndex, week) ->
            week.days
                .firstOrNull { it.date.year == completionYear.year }
                ?.let { day -> weekIndex to (day.date.month.number - 1) }
        }
        .distinctBy { it.second }
        .map { (weekIndex, month) -> Triple(weekIndex, month, month) }

    return monthTransitions
        .zipWithNext { current, next ->
            val (startWeek, monthIndex, _) = current
            val (endWeek, _, _) = next
            val weekCount = endWeek - startWeek
            val width = weekCount.toFloat() * (daySize + daySpacing)

            MonthInfoModel(
                monthIndex = monthIndex,
                width = width,
                startWeek = startWeek,
                endWeek = endWeek - 1
            )
        }
        .let { monthInfos ->
            monthTransitions.lastOrNull()?.let { (startWeek, monthIndex, _) ->
                val weekCount = completionYear.weeks.size - startWeek
                val width = weekCount.toFloat() * (daySize + daySpacing)

                monthInfos + MonthInfoModel(
                    monthIndex = monthIndex,
                    width = width,
                    startWeek = startWeek,
                    endWeek = completionYear.weeks.size - 1
                )
            } ?: monthInfos
        }
}

fun CompletionYearModel.calculateMonthPositions(
    daySize: Float,
    daySpacing: Float
): List<MonthInfoModel> = calculateMonthPositions(this, daySize, daySpacing)