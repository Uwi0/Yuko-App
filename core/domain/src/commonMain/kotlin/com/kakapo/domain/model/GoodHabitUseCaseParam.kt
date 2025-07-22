package com.kakapo.domain.model

import com.kakapo.common.util.startDateAndEndDateOfMonth
import com.kakapo.common.util.todayAtMidnight

data class GoodHabitUseCaseParam(
    val habitId: Long,
    val currentDay: Long,
    val startOfMonth: Long,
    val endOfMonth: Long = 0,
)

fun goodHabitParamFactory(habitId: Long): GoodHabitUseCaseParam {
    val currentDay = todayAtMidnight
    val (starOfMonth, endOfMonth) = startDateAndEndDateOfMonth()

    return GoodHabitUseCaseParam(
        habitId = habitId,
        currentDay = currentDay,
        startOfMonth = starOfMonth,
        endOfMonth = endOfMonth
    )
}