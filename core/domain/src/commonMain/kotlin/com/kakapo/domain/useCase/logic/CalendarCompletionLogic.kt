package com.kakapo.domain.useCase.logic

import com.kakapo.common.util.asEpochDays
import com.kakapo.model.date.DayState
import com.kakapo.model.date.DayValue
import com.kakapo.model.date.MonthModel
import com.kakapo.model.date.WeekModel

fun WeekModel.asCompletionValue(completion: Map<Long, Int>): List<Double> {
    return this.dates.map { day ->
        val dayEpoch = day.toEpochDays()
        val isComplete = (completion[dayEpoch] ?: 0) == 1
        if (isComplete) 1.0 else 0.0
    }
}

fun MonthModel.asCompletionValue(completion: Map<Long, Int>): List<List<DayValue>> {
    return this.weeks.map { weekModel ->
        weekModel.days.map { state ->
            when (state) {
                is DayState.Day -> state.asCompletionValue(completion)
                is DayState.Empty -> DayValue.Empty
            }
        }
    }
}

fun DayState.Day.asCompletionValue(completion: Map<Long, Int>): DayValue{
    val dayEpoch = this.date.asEpochDays()
    val isComplete = (completion[dayEpoch] ?: 0) == 1
    return if(isComplete) DayValue.Day(1.0) else DayValue.Empty
}