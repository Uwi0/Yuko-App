package com.kakapo.domain.useCase.logic

import co.touchlab.kermit.Logger
import com.kakapo.common.util.asEpochDays
import com.kakapo.model.date.DayState
import com.kakapo.model.date.DayValue
import com.kakapo.model.date.MonthModel
import com.kakapo.model.date.WeekModel

fun WeekModel.asCompletionValue(completion: Map<Long, Boolean>): List<Double> {
    return this.dates.map { day ->
        val dayEpoch = day.toEpochDays()
        val isComplete = completion[dayEpoch] ?: false
        if (isComplete) 1.0 else 0.0
    }
}

fun MonthModel.asCompletionValue(completion: Map<Long, Boolean>): List<List<DayValue>> {
    return this.weeks.map { weekModel ->
        weekModel.days.map { state ->
            when (state) {
                is DayState.Day -> state.asCompletionValue(completion)
                is DayState.Empty -> DayValue.Empty
            }
        }
    }
}

fun DayState.Day.asCompletionValue(completion: Map<Long, Boolean>): DayValue{
    val dayEpoch = this.date.asEpochDays()
    val isComplete = completion[dayEpoch] ?: false
    return if(isComplete) DayValue.Day(1.0) else DayValue.Empty
}