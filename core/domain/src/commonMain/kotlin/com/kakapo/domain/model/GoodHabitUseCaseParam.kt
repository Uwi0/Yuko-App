package com.kakapo.domain.model

import com.kakapo.common.util.startDateAndEndDateOfMonth
import com.kakapo.common.util.todayAtMidnight

data class GoodHabitUseCaseParam(
    val habitId: Long,
    val dayAtMidnight: Long,
    val startOfMonth: Long,
    val endOfMonth: Long,
)

fun goodHabitParamFactory(habitId: Long): GoodHabitUseCaseParam {
    val currentDay = todayAtMidnight
    val (starOfMonth, endOfMonth) = startDateAndEndDateOfMonth()

    return GoodHabitUseCaseParam(
        habitId = habitId,
        dayAtMidnight = currentDay,
        startOfMonth = starOfMonth,
        endOfMonth = endOfMonth
    )
}