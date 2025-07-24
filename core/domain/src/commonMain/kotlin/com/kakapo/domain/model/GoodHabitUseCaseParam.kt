package com.kakapo.domain.model

import com.kakapo.common.util.currentDay
import com.kakapo.common.util.startDateAndEndDateOfMonthEpochDays
import com.kakapo.common.util.startDateAndEndDateOfMonthEpochMillis
import com.kakapo.common.util.todayAtMidnight

data class GoodHabitUseCaseParam(
    val habitId: Long,
    val currentDay: Long,
    val startOfMonth: Long,
    val endOfMonth: Long = 0,
)

fun goodHabitParamFactory(habitId: Long): GoodHabitUseCaseParam {
    val (starOfMonth, endOfMonth) = startDateAndEndDateOfMonthEpochDays()

    return GoodHabitUseCaseParam(
        habitId = habitId,
        currentDay = currentDay,
        startOfMonth = starOfMonth,
        endOfMonth = endOfMonth
    )
}