package org.kakapo.project.util.heatmap

import com.kakapo.model.heatmap.CompletionDayModel
import com.kakapo.model.heatmap.CompletionYearModel
import com.kakapo.model.heatmap.MonthInfoModel
import kotlinx.datetime.number

fun CompletionYearModel.calculateMonthPositions(
    daySize: Float,
    daySpacing: Float
): List<MonthInfoModel> = calculateMonthPositionsValue(this, daySize, daySpacing)

fun calculateMonthPositionsValue(
    completionYear: CompletionYearModel,
    daySize: Float,
    daySpacing: Float
): List<MonthInfoModel> {
    val transitions = calculateMonthTransitions(completionYear)
    return transitions.transformItToMonthInfoWith(completionYear, daySize, daySpacing)
}

fun calculateMonthTransitions(
    completionYear: CompletionYearModel
): List<Triple<Int, Int, Int>> {
    return completionYear.weeks
        .withIndex()
        .mapNotNull { (weekIndex, week) ->
            week.days
                .filterIsInstance<CompletionDayModel.Day>()
                .firstOrNull { it.date.year == completionYear.year }
                ?.let { day -> weekIndex to (day.date.month.number - 1) }
        }
        .distinctBy { it.second }
        .map { (weekIndex, month) -> Triple(weekIndex, month, month) }
}

fun List<Triple<Int, Int, Int>>.transformItToMonthInfoWith(
    completionYear: CompletionYearModel,
    daySize: Float,
    daySpacing: Float
): List<MonthInfoModel> {
    return zipWithNext { current, next ->
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
    }.let { monthInfos ->
        this.lastOrNull()?.let { (startWeek, monthIndex, _) ->
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